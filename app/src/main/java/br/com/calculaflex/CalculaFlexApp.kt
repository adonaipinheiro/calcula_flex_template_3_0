package br.com.calculaflex

import android.app.Application
import br.com.calculaflex.data.di.dataModules
import br.com.calculaflex.data.di.networkModules
import br.com.calculaflex.domain.di.domainModules
import br.com.calculaflex.presentation.di.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CalculaFlexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalculaFlexApp)
            modules(domainModules)
            modules(dataModules)
            modules(presentationModules)
            modules(networkModules)
        }
    }
}
