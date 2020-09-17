package hsgpf.some.model.datasource.remote.retrofit.data.github

data class GithubRepositoriesData(
   var nextPageLink: String? = null, var lastPageLink: String? = null,
   val items: List<GithubRepositoryData> = listOf(), @Transient var exception: Exception? = null
)