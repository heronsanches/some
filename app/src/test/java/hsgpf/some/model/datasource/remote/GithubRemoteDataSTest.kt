package hsgpf.some.model.datasource.remote

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import hsgpf.some.model.datasource.remote.exceptions.GithubExceptions
import hsgpf.some.model.datasource.remote.exceptions.headerNotFound
import hsgpf.some.model.datasource.remote.github.GithubRemoteDataS
import hsgpf.some.model.datasource.remote.retrofit.RetrofitUtilsData
import hsgpf.some.model.datasource.remote.retrofit.api.GithubAPI
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.datasource.remote.retrofit.retrofitApiFactory
import hsgpf.some.utils.enums.HttpStatusEnum
import okhttp3.Headers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.FileReader

@RunWith(AndroidJUnit4::class)
class GithubRemoteDataSTest {
   private lateinit var mockWebServer: MockWebServer
   private lateinit var githubRemoteDataS: GithubRemoteDataS
   private lateinit var url: String
   private val testFolder = "model.datasource.remote"

   @Before
   fun setup() {
      mockWebServer = MockWebServer()

      url =
         "${mockWebServer.url("/")}search/repositories?q=language%3Akotlin&sort=stars&page=2&order=desc"

      val retrofitUtilsData = RetrofitUtilsData(
         baseUrl = mockWebServer.url("/").toString(), clazz = GithubAPI::class.java,
         application = ApplicationProvider.getApplicationContext()
      )
      val githubApi = retrofitApiFactory(retrofitUtilsData)
      githubRemoteDataS = GithubRemoteDataS(githubApi)
   }

   @After
   fun finish() {
      mockWebServer.shutdown()
   }

   @Test
   fun searchRepositoriesByLanguage() {
      val bodyResponse = prepareBodyResponseForSearch()
      val headers = prepareHeadersForSearch()

      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = Gson().toJson(bodyResponse), headers)
      )
      val result = githubRemoteDataS.searchRepositoriesByLanguage(
         query = "language:kotlin", sort = "stars", order = "desc", page = 1, resultsPerPage = 15
      )
      assertSuccessfulSearchResult(bodyResponse, result)
   }

   private fun prepareBodyResponseForSearch() = bodyResponse(
      "$testFolder/githubRemoteData_searchRepositoriesByLanguage.json",
      GithubRepositoriesData::class.java
   )

   private fun prepareHeadersForSearch() = Headers.headersOf(
      "link",
      "<https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars&page=2" +
      "&order=asc>; " +
      "rel=\"next\",<https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars" +
      "&page=34&order=asc>;" +
      "rel=\"last\""
   )

   private fun assertSuccessfulSearchResult(bodyResponse: GithubRepositoriesData,
                                            result: GithubRepositoriesData) {
      assertThat(result.items, `is`(bodyResponse.items))
      assertNull(result.exception)
      assertThat(result.nextPage, `is`(2))
      assertThat(result.actualPage, `is`(1))
      assertNull(result.beforePage)
   }

   @Test
   fun searchRepositoriesByLanguage_headerNotFound() {
      // the header name should be "link"
      val headers = Headers.headersOf("linked", "<https://a")
      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = "{}", headers)
      )

      val result = githubRemoteDataS.searchRepositoriesByLanguage(
         query = "language:kotlin", sort = "stars", order = "desc", page = 1,
         resultsPerPage = 15
      )
      assertThat(result.exception is GithubExceptions, `is`(true))
      assertThat(result.exception?.message, `is`(headerNotFound("link")))
   }

   @Test
   fun searchRepositoriesByLanguage_errorProcessingHeader() {
      // the method must catch an error when processing the header"
      val headers = Headers.headersOf("link", "<https://a")
      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = "{}", headers)
      )

      val result = githubRemoteDataS.searchRepositoriesByLanguage(
         query = "language:kotlin", sort = "stars", order = "desc", page = 1,
         resultsPerPage = 15
      )
      assertNotNull(result.exception)
   }

   @Test
   fun searchRelLink() {
      val prev =
         """<https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars&order=desc
               &page=1&per_page=15>; rel="prev""""

      val next =
         """https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars&order=desc
               &page=3&per_page=15>; rel="next""""

      val last =
         """https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars&order=desc
               &page=67&per_page=15>; rel="last""""

      val first =
         """https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars&order=desc
               &page=1&per_page=15>; rel="first""""
      val links = listOf(prev, next, last, first)

      // prev
      var result = githubRemoteDataS.searchRelLink("prev", links)
      assertThat(result, `is`(prev))

      // next
      result = githubRemoteDataS.searchRelLink("next", links)
      assertThat(result, `is`(next))

      // last
      result = githubRemoteDataS.searchRelLink("last", links)
      assertThat(result, `is`(last))

      // first
      result = githubRemoteDataS.searchRelLink("first", links)
      assertThat(result, `is`(first))
   }

   @Test
   fun getPageNumber() {
      val pageLinkUrl =
         """https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars&order=desc
               &page=3&per_page=15>; rel="next""""
      val result = githubRemoteDataS.getPageNumber(pageLinkUrl)
      assertThat(result, `is`(3))
   }

   private fun <T> bodyResponse(jsonPath: String, clazz: Class<T>): T {
      val fileReader = FileReader(ClassLoader.getSystemResource(jsonPath).file)
      return Gson().fromJson(fileReader, clazz)
   }

   private fun mockServerResponse(
      httpStatus: HttpStatusEnum, bodyResponse: String?, headersList: Headers? = null
   ): MockResponse {
      return MockResponse().apply {
         headersList?.run { setHeaders(this) }
         bodyResponse?.run { setBody(this) }
         setResponseCode(httpStatus.code)
      }
   }
}