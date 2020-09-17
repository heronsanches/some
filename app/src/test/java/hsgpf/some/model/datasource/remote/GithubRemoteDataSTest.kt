package hsgpf.some.model.datasource.remote

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import hsgpf.some.model.datasource.remote.exceptions.GithubExceptions
import hsgpf.some.model.datasource.remote.exceptions.headerNotFound
import hsgpf.some.model.datasource.remote.github.GithubRemoteDataS
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

      val githubApi = retrofitApiFactory(
         baseUrl = mockWebServer.url("/").toString(), clazz = GithubAPI::class.java,
         application = ApplicationProvider.getApplicationContext()
      )
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
         query = "q=language:kotlin", sort = "stars", order = "desc", page = 1, resultsPerPage = 15
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
      "&order=asc>;" +
      "rel=\"next\",<https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars" +
      "&page=34&order=asc>;" +
      "rel=\"last\""
   )

   private fun assertSuccessfulSearchResult(bodyResponse: GithubRepositoriesData,
                                            result: GithubRepositoriesData) {
      assertThat(result.items, `is`(bodyResponse.items))
      assertNull(result.exception)

      assertThat(result.nextPageLink,
                 `is`("https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars" +
                      "&page=2&order=asc"))

      assertThat(result.lastPageLink,
                 `is`("https://api.github.com/search/repositories?q=language%3Akotlin&sort=stars" +
                      "&page=34&order=asc"))
   }

   @Test
   fun searchRepositoriesByLanguage_headerNotFound() {
      // the header name should be "link"
      val headers = Headers.headersOf("linked", "<https://a")
      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = "{}", headers)
      )

      val result = githubRemoteDataS.searchRepositoriesByLanguage(
         query = "q=language:kotlin", sort = "stars", order = "desc", page = 1,
         resultsPerPage = 15
      )
      assertThat(result.exception is GithubExceptions, `is`(true))
      assertThat(result.exception?.message, `is`(headerNotFound()))
   }

   @Test
   fun searchRepositoriesByLanguage_errorProcessingHeader() {
      // the method must catch an error when processing the header"
      val headers = Headers.headersOf("link", "<https://a")
      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = "{}", headers)
      )

      val result = githubRemoteDataS.searchRepositoriesByLanguage(
         query = "q=language:kotlin", sort = "stars", order = "desc", page = 1,
         resultsPerPage = 15
      )
      assertNotNull(result.exception)
   }

   @Test
   fun searchRepositoriesByUrl() {
      val bodyResponse = prepareBodyResponseForSearch()
      val headers = prepareHeadersForSearch()

      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = Gson().toJson(bodyResponse), headers)
      )
      val result = githubRemoteDataS.searchRepositoriesByUrl(url)
      assertSuccessfulSearchResult(bodyResponse, result)
   }

   @Test
   fun searchRepositoriesByUrl_errorProcessingHeader() {
      // the method must catch an error when processing the header"
      val headers = Headers.headersOf("link", "<https://a")
      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = "{}", headers)
      )
      val result = githubRemoteDataS.searchRepositoriesByUrl(url)
      assertNotNull(result.exception)
   }

   @Test
   fun searchRepositoriesByUrl_headerNotFound() {
      // the header name should be "link"
      val headers = Headers.headersOf("linked", "<https://a")
      mockWebServer.enqueue(
         mockServerResponse(HttpStatusEnum.OK, bodyResponse = "{}", headers)
      )
      val result = githubRemoteDataS.searchRepositoriesByUrl(url)
      assertThat(result.exception is GithubExceptions, `is`(true))
      assertThat(result.exception?.message, `is`(headerNotFound()))
   }

   @Test
   fun removeFirstCharacter() {
      val toRemove = "<https://api.github.com/search/repositories?q=language%3Akotlin&so"
      val result = githubRemoteDataS.removeFirstCharacter(toRemove)
      assertThat(result, `is`("https://api.github.com/search/repositories?q=language%3Akotlin&so"))
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