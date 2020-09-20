package hsgpf.some.utility.datasource

import androidx.paging.PagingSource
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubOwnerData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class GithubFakePagingSource(
   private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingSource<Int, GithubRepositoryData>() {
   private var actualPage = 0
   private var id = 0L
   private var stars = 1000L

   private fun generatePageData(): List<GithubRepositoryData> {
      val repos = mutableListOf<GithubRepositoryData>()

      for (i in 1..PAGINATION_SIZE) {
         repos.add(GithubRepositoryData(
            name = "name${UUID.randomUUID()}", stargazersCount = stars--,
            forksCount = stars, owner = GithubOwnerData("owner${UUID.randomUUID()}", ""),
            id = ++id
         ))
      }
      actualPage++
      return repos
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepositoryData> {
      val nextPage = params.key ?: 1
      val beforePage: Int? = if (actualPage > 0L) actualPage else null

      var rep = GithubRepositoriesData(
         beforePage = beforePage, actualPage = actualPage, nextPage = nextPage + 1
      )

      return try {
         withContext(dispatcher) {
            rep = rep.copy(items = generatePageData())
            LoadResult.Page(data = rep.items, prevKey = rep.beforePage, nextKey = rep.nextPage)
         }
      } catch (e: Exception) {
         LoadResult.Error(rep.exception ?: e)
      }
   }
}