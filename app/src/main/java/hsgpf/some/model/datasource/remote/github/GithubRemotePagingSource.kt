package hsgpf.some.model.datasource.remote.github

import androidx.paging.PagingSource
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRemotePagingSource(
   private val githubRemoteDataSource: GithubRemoteDataSource,
   private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingSource<Int, GithubRepositoryData>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepositoryData> {
      var repositories = GithubRepositoriesData()

      return try {
         val nextPage = params.key ?: 1

         withContext(dispatcher) {
            repositories = githubRemoteDataSource.searchRepositoriesByLanguage(
               "language:kotlin", "desc", "stars", nextPage, PAGINATION_SIZE
            )

            LoadResult.Page(
               data = repositories.items, prevKey = repositories.beforePage,
               nextKey = repositories.nextPage
            )
         }
      } catch (e: Exception) {
         LoadResult.Error(repositories.exception ?: e)
      }
   }
}