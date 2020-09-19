package hsgpf.some.model.repository.remote.github

import hsgpf.some.model.datasource.remote.github.GithubRemotePagingSource

class GithubRemoteR(githubRemotePagingSource: GithubRemotePagingSource)
   : GithubRemoteRepository {
   override val repositoriesPagingSource = githubRemotePagingSource
}