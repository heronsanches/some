package hsgpf.some.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.repository.remote.github.GithubRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GithubTopKotlinProjectsViewModel(
   private val githubRemoteRepository: GithubRemoteRepository,
   private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {
   private val repositories = MutableLiveData<GithubRepositoriesData>()
   private val loadingRepositories = MutableLiveData<Boolean>()
   var onSavedInstance = false
   var nextPage = 1
   var actualPage = 1

   fun repositories(): LiveData<GithubRepositoriesData> = repositories
   fun loadingRepositories(): LiveData<Boolean> = loadingRepositories

   fun searchRepositories(page: Int) {
      loadingRepositories.value = true

      launch {
         repositories.value = withContext(dispatcher) {
            githubRemoteRepository.searchRepositories(
               "language:kotlin", "stars", "desc", page, PAGINATION_SIZE
            )
         }
         loadingRepositories.value = false
      }
   }
}