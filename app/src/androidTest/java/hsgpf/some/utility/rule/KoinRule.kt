package hsgpf.some.utility.rule

import androidx.test.core.app.ApplicationProvider
import hsgpf.some.CustomApplication
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.test.KoinTest

class KoinRule : TestWatcher(), KoinTest {

   override fun starting(description: Description?) {
      ApplicationProvider.getApplicationContext<CustomApplication>().initiateKoinModules()
      super.starting(description)
   }

   override fun finished(description: Description?) {
      ApplicationProvider.getApplicationContext<CustomApplication>().finishKoin()
      super.finished(description)
   }
}