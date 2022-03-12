package br.com.calculaflex.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.calculaflex.R
import br.com.calculaflex.data.remote.datasource.AppRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.repository.AppRepositoryImpl
import br.com.calculaflex.data.repository.UserRepositoryImpl
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.extensions.startDeeplink
import br.com.calculaflex.presentation.base.auth.BaseAuthFragment
import br.com.calculaflex.presentation.base.auth.NAVIGATION_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home

    private lateinit var tvHomeHelloUser: TextView
    private lateinit var rvHomeDashboard: RecyclerView

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)

        registerObserver()
        homeViewModel.getDashboardMenu()
    }

    private fun setUpView(view: View) {
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
        tvHomeHelloUser = view.findViewById(R.id.tvHomeHelloUser)
    }

    private fun registerObserver() {
        homeViewModel.dashboardItemsState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    setUpMenu(it.data)
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })

        homeViewModel.headerState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    tvHomeHelloUser.text = "Carregando o usuario"
                }

                is RequestState.Success -> {
                    val (title, userName) = it.data
                    tvHomeHelloUser.text = String.format(title, userName)
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })

        homeViewModel.signOutState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    NavHostFragment.findNavController(this).navigate(
                        R.id.login_graph
                    )
                }

                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })

    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter = HomeAdapter(items, this::clickItem)
    }

    private fun clickItem(item: DashboardItem) {
        item.onDisabledListener.let {
            it?.invoke(requireContext())
        }

        if (item.onDisabledListener == null) {
            when (item.feature) {
                "SIGN_OUT" -> {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Calcula Flex")
                    builder.setMessage("Deseja sair do aplicativo?")
                    builder.setPositiveButton("Sim") { dialog, which ->
                        homeViewModel.signOut()
                    }

                    builder.setNegativeButton("Não") { dialog, which ->

                    }

                    builder.show()
                }

                "ETHANOL_OR_GASOLINE" -> {
                    startDeeplink("${item.action.deeplink}?id=${homeViewModel.userLogged?.id}")
                }

                else -> {
//                    item.action.deeplink.let {
//                        startDeeplink(item.action.deeplink)
//                    }
                }
            }
        }
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
