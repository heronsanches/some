package hsgpf.some

import android.app.Application
import hsgpf.some.di.activityHelperModule
import hsgpf.some.di.remoteRepositoryModule
import hsgpf.some.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class CustomApplication : Application() {

   fun initiateKoinModules() {
      startKoin {
         androidContext(this@CustomApplication)
         modules(activityHelperModule, remoteRepositoryModule, viewModelModule)
      }
   }

   fun finishKoin() = stopKoin()
}