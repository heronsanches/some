package hsgpf.some.model.repository.remote.github

import hsgpf.some.model.datasource.remote.github.GithubRemotePagingSource

interface GithubRemoteRepository {
   val repositoriesPagingSource: GithubRemotePagingSource
}