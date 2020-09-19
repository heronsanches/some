package hsgpf.some.model.repository.remote.github

import hsgpf.some.model.datasource.remote.github.GithubRemotePagedDataSource
import org.junit.Before
import org.mockito.Mockito.mock

class GithubRemoteRTest {
   private lateinit var githubRemoteR: GithubRemoteR
   private lateinit var githubRemotePagedDataSource: GithubRemotePagedDataSource

   @Before
   fun setup() {
      githubRemotePagedDataSource = mock(GithubRemotePagedDataSource::class.java)
      githubRemoteR = GithubRemoteR(githubRemotePagedDataSource)
   }

   /*@Test TODO:
   fun pagedRepositories() {

   }*/
}