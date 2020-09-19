package hsgpf.some.viewModel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.repository.remote.github.GithubRemoteRepository

class GithubTopKotlinProjectsViewModel(githubRemoteRepository: GithubRemoteRepository)
   : BaseViewModel() {
   val repositories = Pager(
      PagingConfig(pageSize = PAGINATION_SIZE, initialLoadSize = PAGINATION_SIZE * 2,
                   enablePlaceholders = false, prefetchDistance = PAGINATION_SIZE)
   ) { githubRemoteRepository.repositoriesPagingSource }.flow.cachedIn(this)
}