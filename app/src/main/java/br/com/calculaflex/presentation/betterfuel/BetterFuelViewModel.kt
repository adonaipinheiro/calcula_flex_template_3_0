package br.com.calculaflex.presentation.betterfuel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.enums.FuelType
import br.com.calculaflex.domain.entity.holder.BetterFuelHolder
import br.com.calculaflex.domain.usecases.CalculateBetterFuelUseCase
import br.com.calculaflex.domain.usecases.GetCarUseCase
import br.com.calculaflex.domain.usecases.SaveCarUseCase
import kotlinx.coroutines.launch

class BetterFuelViewModel(
    private val saveCarUseCase: SaveCarUseCase,
    private val calculateBetterFuelUseCase: CalculateBetterFuelUseCase,
    private val getCarUseCase: GetCarUseCase
) : ViewModel() {
    var calculateState = MutableLiveData<RequestState<FuelType>>()

    val carSelectedState = MutableLiveData<RequestState<Car>>()

    fun getCar(id: String) {
        viewModelScope.launch {
            carSelectedState.value = getCarUseCase.findBy(id)
        }
    }

    fun calculateBetterFuel(
        vehicle: String,
        kmGasolinePerLiter: Double,
        kmEthanolPerLiter: Double,
        priceGasolinePerLiter: Double,
        priceEthanolPerLiter: Double
    ) {
        val car = Car(
            vehicle,
            kmGasolinePerLiter,
            kmEthanolPerLiter,
            priceGasolinePerLiter,
            priceEthanolPerLiter,
            ""
        )

        viewModelScope.launch {
            val response = saveCarUseCase.save(car)
            when (response) {
                is RequestState.Success -> {
                    val params = CalculateBetterFuelUseCase.Params(
                        BetterFuelHolder(
                            car.kmEthanolPerLiter,
                            car.kmGasolinePerLiter,
                            car.priceEthanolPerLiter,
                            car.priceGasolinePerLiter
                        )
                    )
                    calculateState.value = calculateBetterFuelUseCase.calculate(params)
                }
                is RequestState.Error -> {
                    calculateState.value = RequestState.Error(Exception("Não foi possível calcular"))
                }
                is RequestState.Loading -> {
                    calculateState.value = RequestState.Loading
                }
            }
        }
    }
}

