package hsgpf.some.model.repository.remote.github

import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData

interface GithubRemoteRepository {

   fun searchRepositories(
      query: String, sort: String, order: String, page: Int, resultsPerPage: Int
   ): GithubRepositoriesData
}