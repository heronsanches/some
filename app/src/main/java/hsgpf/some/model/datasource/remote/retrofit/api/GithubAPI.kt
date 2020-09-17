package hsgpf.some.model.datasource.remote.retrofit.api

import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

const val BASE_URL_GITHUB_API = "https://api.github.com/"

interface GithubAPI {

   /** Search repositories getting the first 1000 results. It uses pagination.
    * See more at https://docs.github.com/en/rest/reference/search#search-repositories.*/
   @GET("search/repositories")
   fun searchRepositories(
      @Header("accept") acceptHeader: String = "application/vnd.github.v3+json",
      @Query("q") q: String, @Query("sort") sort: String? = null,
      @Query("order") order: String? = null, @Query("page") page: Int,
      @Query("per_page") perPage: Int
   ): Call<GithubRepositoriesData>

   @GET
   fun searchRepositoriesByUrl(
      @Header("accept") acceptHeader: String = "application/vnd.github.v3+json", @Url url: String
   ): Call<GithubRepositoriesData>
}