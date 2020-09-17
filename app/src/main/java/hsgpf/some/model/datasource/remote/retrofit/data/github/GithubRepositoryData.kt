package hsgpf.some.model.datasource.remote.retrofit.data.github

import com.google.gson.annotations.SerializedName

data class GithubRepositoryData(
   val name: String, @SerializedName("stargazers_count") val stargazersCount: Long,
   @SerializedName("forks_count") val forksCount: Long, val owner: GithubOwnerData, val id: Long
)