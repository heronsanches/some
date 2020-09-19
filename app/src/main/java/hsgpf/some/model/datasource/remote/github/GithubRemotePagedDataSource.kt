package hsgpf.some.model.datasource.remote.github

import androidx.paging.PageKeyedDataSource
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData

class GithubRemotePagedDataSource(
   private val githubRemoteDataSource: GithubRemoteDataSource
) : PageKeyedDataSource<Int, GithubRepositoryData>() {

   override fun loadInitial(params: LoadInitialParams<Int>,
                            callback: LoadInitialCallback<Int, GithubRepositoryData>) {
      val repositories = githubRemoteDataSource.searchRepositoriesByLanguage(
         "language:kotlin", "desc", "stars", 1, PAGINATION_SIZE
      )
      callback.onResult(repositories.items, repositories.beforePage, repositories.nextPage)
   }

   override fun loadAfter(params: LoadParams<Int>,
                          callback: LoadCallback<Int, GithubRepositoryData>) {
      val repositories = githubRemoteDataSource.searchRepositoriesByLanguage(
         "language:kotlin", "desc", "stars", params.key, PAGINATION_SIZE
      )
      callback.onResult(repositories.items, repositories.nextPage)
   }

   override fun loadBefore(p: LoadParams<Int>, c: LoadCallback<Int, GithubRepositoryData>) {}
}