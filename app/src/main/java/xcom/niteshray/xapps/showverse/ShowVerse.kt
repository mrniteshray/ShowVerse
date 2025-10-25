package xcom.niteshray.xapps.showverse

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import xcom.niteshray.xapps.showverse.core.di.appModule

class ShowVerse : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ShowVerse)
            modules(appModule)
        }
    }
}