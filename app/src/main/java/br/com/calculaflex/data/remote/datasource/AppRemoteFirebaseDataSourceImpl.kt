package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.data.remote.utils.firebase.RemoteConfigKeys
import br.com.calculaflex.domain.entity.DashboardMenu
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.extensions.fromRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AppRemoteFirebaseDataSourceImpl : AppRemoteDataSource {

    override suspend fun getMinVersionApp(): RequestState<Int> {
        val minVersion = Gson().fromRemoteConfig("min_version_app", Int::class.java) ?: 0
        return RequestState.Success(minVersion)
    }

    override suspend fun getDashboardMenu(): RequestState<DashboardMenu> {
        val dashboardMenu = Gson().fromRemoteConfig(RemoteConfigKeys.MENU_DASHBOARD, DashboardMenu::class.java)
        if(dashboardMenu == null) {
            return RequestState.Error(Exception("Não foi possível carregar o menu principal"))
        } else {
            return RequestState.Success(dashboardMenu)
        }
    }

}
