package hsgpf.some.model.repository.remote.github

import hsgpf.some.model.datasource.remote.github.GithubRemoteDataSource
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GithubRemoteRTest {
   private lateinit var githubRemoteDataSource: GithubRemoteDataSource
   private lateinit var githubRemoteR: GithubRemoteR

   @Before
   fun setup() {
      githubRemoteDataSource = mock(GithubRemoteDataSource::class.java)
      githubRemoteR = GithubRemoteR(githubRemoteDataSource)
   }

   @Test
   fun searchRepositories() {
      val outputData = GithubRepositoriesData()

      `when`(githubRemoteDataSource.searchRepositoriesByLanguage("query", "sort", "order", 1, 15))
         .thenReturn(outputData)
      val result = githubRemoteR.searchRepositories("query", "sort", "order", 1, 15)
      assertThat(result, `is`(outputData))
   }

   @Test
   fun searchRepositoriesByUrl() {
      val url =
         "https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars&page=2&order=desc"
      val outputData = GithubRepositoriesData()
      `when`(githubRemoteDataSource.searchRepositoriesByUrl(url)).thenReturn(outputData)
      assertThat(githubRemoteR.searchRepositoriesByUrl(url), `is`(outputData))
   }
}