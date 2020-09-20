package hsgpf.some.model.datasource.remote.github

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubOwnerData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import hsgpf.test.utility.MainTestCoroutineDispatcherRule
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GithubRemotePagingSourceTest {
   @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
   @get:Rule val mainTestCoroutineDispatcherRule = MainTestCoroutineDispatcherRule()

   @Test
   fun load() = runBlockingTest {
      val githubRemoteDataSource = mock(GithubRemoteDataSource::class.java)

      val githubRemotePagingSource = GithubRemotePagingSource(
         githubRemoteDataSource, mainTestCoroutineDispatcherRule.dispatcher
      )

      val repositories = GithubRepositoriesData(
         nextPage = 2, actualPage = 1,
         items = listOf(
            GithubRepositoryData("name", 100, 89, GithubOwnerData("login", "https://"), 11)
         )
      )

      val params =
         PagingSource.LoadParams.Prepend(1, PAGINATION_SIZE * 2, false, PAGINATION_SIZE * 3)

      `when`(githubRemoteDataSource.searchRepositoriesByLanguage(
         "language:kotlin", "desc", "stars", 1, PAGINATION_SIZE)
      ).thenReturn(repositories)

      val expectedResult = PagingSource.LoadResult.Page(
         data = repositories.items, prevKey = repositories.beforePage,
         nextKey = repositories.nextPage
      )
      val result = githubRemotePagingSource.load(params)
      assertThat(result, `is`(expectedResult))
   }
}