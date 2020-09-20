package hsgpf.some.viewModel

import androidx.paging.cachedIn
import hsgpf.some.model.repository.remote.github.GithubRemoteRepository

class GithubTopKotlinProjectsViewModel(githubRemoteRepository: GithubRemoteRepository)
   : BaseViewModel() {
   val repositoriesFlow = githubRemoteRepository.repositoriesFlow().cachedIn(this)
}