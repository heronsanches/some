package hsgpf.some.model.repository.remote.github

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData

interface GithubRemoteRepository {
   val pagedRepositories: LiveData<PagedList<GithubRepositoryData>>
}