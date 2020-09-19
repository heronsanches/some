package hsgpf.some.viewModel

import hsgpf.some.model.repository.remote.github.GithubRemoteRepository
import org.junit.Before
import org.mockito.Mockito.mock

class GithubTopKotlinProjectsViewModelTest {
   private lateinit var githubTopKotlinProjectsViewModel: GithubTopKotlinProjectsViewModel
   private lateinit var githubRemoteRepository: GithubRemoteRepository

   @Before
   fun setup() {
      githubRemoteRepository = mock(GithubRemoteRepository::class.java)
      githubTopKotlinProjectsViewModel = GithubTopKotlinProjectsViewModel(githubRemoteRepository)
   }

   /*@Test TODO:
   fun repositories() = runBlockingTest {

   }*/
}