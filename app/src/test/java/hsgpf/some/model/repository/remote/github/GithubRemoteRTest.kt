package hsgpf.some.model.repository.remote.github

import hsgpf.some.model.datasource.remote.github.GithubRemotePagingSource
import org.junit.Before
import org.mockito.Mockito.mock

class GithubRemoteRTest {
   private lateinit var githubRemoteR: GithubRemoteR
   private lateinit var githubRemotePagingSource: GithubRemotePagingSource

   @Before
   fun setup() {
      githubRemotePagingSource = mock(GithubRemotePagingSource::class.java)
      githubRemoteR = GithubRemoteR(githubRemotePagingSource)
   }

   /*@Test TODO:
   fun pagedRepositories() {

   }*/
}