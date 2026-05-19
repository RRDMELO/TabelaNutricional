package com.rocketseat.RRM.tabelanutricional

import android.app.Application
import com.rocketseat.RRM.tabelanutricional.core.di.AppModules.dataModule
import com.rocketseat.RRM.tabelanutricional.core.di.AppModules.domainModule
import com.rocketseat.RRM.tabelanutricional.core.di.AppModules.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                uiModule,
                domainModule,
                dataModule
            )
        }
    }
}
