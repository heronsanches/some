package hsgpf.some.utility.rule

import androidx.test.espresso.IdlingRegistry
import hsgpf.some.utility.idlingresource.DataBindingIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DatabindingIdlingRule : TestWatcher() {
   val resource = DataBindingIdlingResource()

   override fun starting(description: Description?) {
      super.starting(description)
      IdlingRegistry.getInstance().register(resource)
   }

   override fun finished(description: Description?) {
      super.finished(description)
      IdlingRegistry.getInstance().unregister(resource)
   }
}