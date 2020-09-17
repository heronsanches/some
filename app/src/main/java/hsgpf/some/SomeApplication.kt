@file:Suppress("unused")

package hsgpf.some

import android.app.Application
import hsgpf.some.di.activityHelperModule
import hsgpf.some.di.remoteDataSourceModule
import hsgpf.some.di.remoteRepositoryModule
import hsgpf.some.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SomeApplication : Application() {

   override fun onCreate() {
      super.onCreate()
      initializeKoin()
   }

   private fun initializeKoin() {
      startKoin {
         androidContext(this@SomeApplication)

         modules(activityHelperModule, remoteDataSourceModule, remoteRepositoryModule,
                 viewModelModule)
      }
   }
}