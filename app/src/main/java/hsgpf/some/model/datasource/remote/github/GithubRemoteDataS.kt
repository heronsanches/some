package hsgpf.some.model.datasource.remote.github

import androidx.annotation.VisibleForTesting
import hsgpf.some.model.datasource.remote.exceptions.GithubExceptions
import hsgpf.some.model.datasource.remote.exceptions.headerNotFound
import hsgpf.some.model.datasource.remote.exceptions.headerPropertyNotFound
import hsgpf.some.model.datasource.remote.retrofit.api.GithubAPI
import hsgpf.some.model.datasource.remote.retrofit.data.ResponseData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.datasource.remote.retrofit.processResponse

class GithubRemoteDataS(private val githubAPI: GithubAPI) : GithubRemoteDataSource {

   override fun searchRepositoriesByLanguage(
      query: String, sort: String, order: String, page: Int, resultsPerPage: Int
   ): GithubRepositoriesData {
      return try {
         val response = githubAPI.searchRepositories(
            q = query, sort = sort, order = order, page = page, perPage = resultsPerPage
         ).execute()
         formatResponse(processResponse(response))
      } catch (e: Throwable) {
         GithubRepositoriesData(exception = e)
      }
   }

   private fun formatResponse(
      processResponse: ResponseData<GithubRepositoriesData>
   ): GithubRepositoriesData {
      return processResponse.headers["link"]?.run {
         val links = split(",")
         val nextPageLink = searchRelLink("next", links)
         val nextPage = getPageNumber(nextPageLink)

         val actualPage =
            if (nextPage > 1) nextPage - 1
            else nextPage

         val beforePage: Int? =
            if (actualPage > 1) actualPage - 1
            else null

         val repositories = processResponse.payload?.apply {
            this.actualPage = actualPage
            this.nextPage = nextPage
            this.beforePage = beforePage
         }
         repositories
      } ?: throw GithubExceptions(headerNotFound("link"))
   }

   @VisibleForTesting
   fun searchRelLink(rel: String, links: List<String>): String {
      return links.find { it.contains("rel=\"$rel\"") }
             ?: throw GithubExceptions(headerPropertyNotFound(headerName = "link", "rel=$rel"))
   }

   @VisibleForTesting
   fun getPageNumber(pageLinkUrl: String): Int {
      return pageLinkUrl.split(Regex("&page="))[1].split("&")[0].toInt()
   }
}