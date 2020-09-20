package hsgpf.some.viewModel

import androidx.paging.PagingData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import hsgpf.some.model.repository.remote.github.GithubRemoteRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GithubTopKotlinProjectsViewModelTest {
   private lateinit var githubTopKotlinProjectsViewModel: GithubTopKotlinProjectsViewModel
   private lateinit var githubRemoteRepository: GithubRemoteRepository

   /** Sanity check.*/
   @InternalCoroutinesApi
   @Test
   fun repositories() {
      githubRemoteRepository = mock(GithubRemoteRepository::class.java)

      val output = object : Flow<PagingData<GithubRepositoryData>> {

         @InternalCoroutinesApi
         override suspend fun collect(coll: FlowCollector<PagingData<GithubRepositoryData>>) {; }
      }
      `when`(githubRemoteRepository.repositoriesFlow()).thenReturn(output)
      githubTopKotlinProjectsViewModel = GithubTopKotlinProjectsViewModel(githubRemoteRepository)
   }
}