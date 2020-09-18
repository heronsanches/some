package hsgpf.some.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.repository.remote.github.GithubRemoteRepository
import hsgpf.test.utility.MainTestCoroutineDispatcherRule
import hsgpf.test.utility.pause
import hsgpf.test.utility.resume
import hsgpf.utility.getOrAwaitValue
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GithubTopKotlinProjectsViewModelTest {
   @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
   @get:Rule val mainTestCoroutineDispatcherRule = MainTestCoroutineDispatcherRule()
   private lateinit var githubTopKotlinProjectsViewModel: GithubTopKotlinProjectsViewModel
   private lateinit var githubRemoteRepository: GithubRemoteRepository

   @Before
   fun setup() {
      githubRemoteRepository = mock(GithubRemoteRepository::class.java)

      githubTopKotlinProjectsViewModel = GithubTopKotlinProjectsViewModel(
         githubRemoteRepository, mainTestCoroutineDispatcherRule.dispatcher
      )
   }

   @Test
   fun searchRepositories() = runBlockingTest {
      checkInitialLiveDataValues()
      val expected = GithubRepositoriesData()

      `when`(githubRemoteRepository.searchRepositories("language:kotlin", "stars", "desc", 1, 15))
         .thenReturn(expected)
      githubTopKotlinProjectsViewModel.searchRepositories(1)

      // loading repositories
      assertThat(githubTopKotlinProjectsViewModel.loadingRepositories().getOrAwaitValue(),
                 `is`(true))
      mainTestCoroutineDispatcherRule.resume()

      // must has [expected] [GithubRepositoriesData] value
      assertThat(githubTopKotlinProjectsViewModel.repositories().getOrAwaitValue(), `is`(expected))

      // should finished loading repositories
      assertThat(githubTopKotlinProjectsViewModel.loadingRepositories().getOrAwaitValue(),
                 `is`(false))
   }

   private fun checkInitialLiveDataValues() {
      mainTestCoroutineDispatcherRule.pause()
      assertNull(githubTopKotlinProjectsViewModel.repositories().value)
      assertNull(githubTopKotlinProjectsViewModel.loadingRepositories().value)
   }
}