package hsgpf.some.model.repository.remote.github

import androidx.paging.PagingSource
import hsgpf.some.model.datasource.remote.github.GithubRemotePagingSource
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import org.junit.Test
import org.mockito.Mockito.mock

class GithubRemoteRTest {
   private lateinit var githubRemoteR: GithubRemoteR
   private lateinit var githubRemotePagingSource: PagingSource<Int, GithubRepositoryData>

   @Test
   fun sanityTest() {
      githubRemotePagingSource = mock(GithubRemotePagingSource::class.java)
      githubRemoteR = GithubRemoteR(githubRemotePagingSource)
   }
}