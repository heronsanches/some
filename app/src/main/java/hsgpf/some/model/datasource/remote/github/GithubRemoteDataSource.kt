package hsgpf.some.model.datasource.remote.github

import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData

interface GithubRemoteDataSource {

   fun searchRepositoriesByLanguage(
      query: String, sort: String, order: String, page: Int, resultsPerPage: Int
   ): GithubRepositoriesData
}