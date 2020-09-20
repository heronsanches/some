package hsgpf.some.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingSource
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import hsgpf.some.utility.datasource.GithubFakePagingSource
import hsgpf.some.utility.rule.DatabindingIdlingRule
import hsgpf.some.utility.rule.KoinRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class GithubTopKotlinProjectsActivityTest : KoinTest {
   @get:Rule val dataBindingIdlingRule = DatabindingIdlingRule()
   @get:Rule val koinRule = KoinRule()
   private var scenario: ActivityScenario<GithubTopKotlinProjectsActivity>? = null
   private var activity: AppCompatActivity? = null

   private val remoteDataSourceModule = module {
      factory { GithubFakePagingSource() as PagingSource<Int, GithubRepositoryData> }
   }

   private fun defineActivityAndScenario() {
      val intent = Intent(ApplicationProvider.getApplicationContext(),
                          GithubTopKotlinProjectsActivity::class.java)
      scenario = ActivityScenario.launch(intent)
      scenario?.onActivity { activity -> this.activity = activity }
   }

   @After
   fun finish() {
      scenario?.close()
   }

   @Test
   fun initialLoad() { // TODO:
      loadKoinModules(remoteDataSourceModule)
      defineActivityAndScenario()
   }
}