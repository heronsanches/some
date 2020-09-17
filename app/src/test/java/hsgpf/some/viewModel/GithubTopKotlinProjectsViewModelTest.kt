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
   fun searchRepositoriesInitialPage() = runBlockingTest {
      checkInitialLiveDataValues()

      // must has a non null [GithubRepositoriesData] value
      val expected = GithubRepositoriesData()
      `when`(githubRemoteRepository.searchRepositories("q=language:kotlin", "stars", "desc", 1, 15))
         .thenReturn(expected)
      githubTopKotlinProjectsViewModel.searchRepositoriesInitialPage()
      assertThat(githubTopKotlinProjectsViewModel.repositories().getOrAwaitValue(), `is`(expected))
   }

   @Test
   fun searchRepositoriesByUrl() {
      checkInitialLiveDataValues()
      val expected = GithubRepositoriesData()
      `when`(githubRemoteRepository.searchRepositoriesByUrl("")).thenReturn(expected)
      githubTopKotlinProjectsViewModel.searchRepositoriesByUrl("")
      assertThat(githubTopKotlinProjectsViewModel.repositories().getOrAwaitValue(), `is`(expected))
   }

   private fun checkInitialLiveDataValues() {
      // must has a null [GithubRepositoriesData] value when start view model
      mainTestCoroutineDispatcherRule.pause()
      assertNull(githubTopKotlinProjectsViewModel.repositories().value)
      mainTestCoroutineDispatcherRule.resume()
   }
}