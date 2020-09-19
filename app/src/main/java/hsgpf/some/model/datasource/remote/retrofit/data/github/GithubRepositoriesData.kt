package hsgpf.some.model.datasource.remote.retrofit.data.github

data class GithubRepositoriesData(
   var beforePage: Int? = null, var actualPage: Int = 1, var nextPage: Int = 1,
   val items: List<GithubRepositoryData> = listOf(), var exception: Throwable? = null
)