package hsgpf.test.utility

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainTestCoroutineDispatcherRule : TestWatcher() {
   val dispatcher = TestCoroutineDispatcher()

   override fun starting(description: Description?) {
      super.starting(description)
      Dispatchers.setMain(dispatcher)
   }

   override fun finished(description: Description?) {
      super.finished(description)
      dispatcher.cleanupTestCoroutines()
      Dispatchers.resetMain()
   }
}

fun MainTestCoroutineDispatcherRule.pause() = dispatcher.pauseDispatcher()
fun MainTestCoroutineDispatcherRule.resume() = dispatcher.resumeDispatcher()