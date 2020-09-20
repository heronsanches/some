package hsgpf.some.model.repository.remote.github

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import kotlinx.coroutines.flow.Flow

class GithubRemoteR(private val githubRemotePagingSource: PagingSource<Int, GithubRepositoryData>)
   : GithubRemoteRepository {

   override fun repositoriesFlow(): Flow<PagingData<GithubRepositoryData>> {
      return Pager(
         PagingConfig(pageSize = PAGINATION_SIZE, initialLoadSize = PAGINATION_SIZE * 2,
                      enablePlaceholders = false, prefetchDistance = PAGINATION_SIZE,
                      maxSize = PAGINATION_SIZE * 5)
      ) { githubRemotePagingSource }.flow
   }
}