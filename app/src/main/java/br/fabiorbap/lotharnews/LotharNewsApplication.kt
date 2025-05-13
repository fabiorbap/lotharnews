package br.fabiorbap.lotharnews

import android.app.Application
import br.fabiorbap.lotharnews.di.ApplicationModule
import br.fabiorbap.lotharnews.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

class LotharNewsApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(ApplicationModule().module, NetworkModule().module)
            androidContext(this@LotharNewsApplication)
            androidLogger(Level.DEBUG)
        }
    }

}