package hsgpf.some.model.repository.remote.github

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import hsgpf.some.model.datasource.remote.github.GithubRemotePagedDataSource
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData

class GithubRemoteR(private val githubRemotePagedDataSource: GithubRemotePagedDataSource)
   : GithubRemoteRepository {
   private val searchPagedRepositoriesConfig = PagedList.Config.Builder().apply {
      setPageSize(PAGINATION_SIZE)
      setInitialLoadSizeHint(PAGINATION_SIZE * 2)
      setEnablePlaceholders(false)
   }.build()

   private val pagedRepositoriesDataSource =
      object : DataSource.Factory<Int, GithubRepositoryData>() {
         override fun create(): DataSource<Int, GithubRepositoryData> = githubRemotePagedDataSource
      }

   override val pagedRepositories: LiveData<PagedList<GithubRepositoryData>> =
      LivePagedListBuilder(pagedRepositoriesDataSource, searchPagedRepositoriesConfig).build()
}