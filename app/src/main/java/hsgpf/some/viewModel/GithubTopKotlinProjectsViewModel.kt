package hsgpf.some.viewModel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import hsgpf.some.model.repository.remote.github.GithubRemoteRepository

class GithubTopKotlinProjectsViewModel(githubRemoteRepository: GithubRemoteRepository)
   : BaseViewModel() {
   val repositories: LiveData<PagedList<GithubRepositoryData>> =
      githubRemoteRepository.pagedRepositories
}