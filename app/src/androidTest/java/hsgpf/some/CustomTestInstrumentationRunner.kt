package hsgpf.some

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import hsgpf.some.utility.setupDeviceConfigurations

class CustomTestInstrumentationRunner : AndroidJUnitRunner() {

   @Throws(InstantiationException::class, IllegalAccessException::class,
           ClassNotFoundException::class)
   override fun newApplication(cl: ClassLoader?, className: String?, context: Context?)
      : Application? {
      return super.newApplication(cl, CustomApplication::class.java.name, context)
   }

   override fun onStart() {
      setupDeviceConfigurations()
      super.onStart()
   }
}