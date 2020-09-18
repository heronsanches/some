package hsgpf.some.model.repository.remote.github

import hsgpf.some.model.datasource.remote.github.GithubRemoteDataSource
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData

class GithubRemoteR(private val githubRemoteDataSource: GithubRemoteDataSource)
   : GithubRemoteRepository {

   override fun searchRepositories(
      query: String, sort: String, order: String, page: Int, resultsPerPage: Int
   ): GithubRepositoriesData {
      return githubRemoteDataSource.searchRepositoriesByLanguage(
         query, sort, order, page, resultsPerPage
      )
   }
}