package br.com.calculaflex.domain.utils

import br.com.calculaflex.domain.entity.enums.FuelType
import br.com.calculaflex.domain.entity.holder.BetterFuelHolder

class FuelCalculator {

    fun betterFuel(
        betterFuelHolder: BetterFuelHolder
    ): FuelType {

        val performanceOfMyCar = betterFuelHolder.ethanolAverage / betterFuelHolder.gasAverage

        val priceOfFuelIndice = betterFuelHolder.ethanolPrice / betterFuelHolder.gasPrice

        return if (priceOfFuelIndice <= performanceOfMyCar) {
            FuelType.ETHANOL
        } else {
            FuelType.GASOLINE
        }
    }
}
