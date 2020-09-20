package hsgpf.some.model.repository.remote.github

import androidx.paging.PagingData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import kotlinx.coroutines.flow.Flow

interface GithubRemoteRepository {
   fun repositoriesFlow(): Flow<PagingData<GithubRepositoryData>>
}