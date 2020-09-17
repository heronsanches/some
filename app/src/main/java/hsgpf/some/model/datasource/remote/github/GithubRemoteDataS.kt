package hsgpf.some.model.datasource.remote.github

import androidx.annotation.VisibleForTesting
import hsgpf.some.model.datasource.remote.exceptions.GithubExceptions
import hsgpf.some.model.datasource.remote.exceptions.headerNotFound
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
      } catch (e: Exception) {
         GithubRepositoriesData(exception = e)
      }
   }

   private fun formatResponse(
      processResponse: ResponseData<GithubRepositoriesData>
   ): GithubRepositoriesData {
      return processResponse.headers["link"]?.run {
         val linkProcessing = split(""">;rel="next",""")
         val nextPage = removeFirstCharacter(linkProcessing[0])
         val lastPage = removeFirstCharacter(linkProcessing[1].split(""">;rel="last"""")[0])

         val repositories = processResponse.payload?.apply {
            nextPageLink = nextPage
            lastPageLink = lastPage
         }
         repositories
      } ?: throw GithubExceptions(headerNotFound())
   }

   override fun searchRepositoriesByUrl(url: String): GithubRepositoriesData {
      return try {
         val response = githubAPI.searchRepositoriesByUrl(url = url).execute()
         return formatResponse(processResponse(response))
      } catch (e: Exception) {
         GithubRepositoriesData(exception = e)
      }
   }

   @VisibleForTesting
   fun removeFirstCharacter(text: String) = text.removeRange(0, 1)
}