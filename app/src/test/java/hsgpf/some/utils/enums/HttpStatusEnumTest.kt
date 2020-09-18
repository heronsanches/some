package hsgpf.some.utils.enums

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class HttpStatusEnumTest {

   @Test
   fun getByCode() {
      // ok
      var result = HttpStatusEnum.getByCode(200)
      assertThat(result, `is`(HttpStatusEnum.OK))
      assertThat(result.code, `is`(200))
      assertThat(result.description, `is`("OK"))

      // created
      result = HttpStatusEnum.getByCode(201)
      assertThat(result, `is`(HttpStatusEnum.CREATED))
      assertThat(result.code, `is`(201))
      assertThat(result.description, `is`("Created"))

      // permanent_redirected
      result = HttpStatusEnum.getByCode(308)
      assertThat(result, `is`(HttpStatusEnum.PERMANENT_REDIRECTED))
      assertThat(result.code, `is`(308))
      assertThat(result.description, `is`("Permanent Redirect"))

      // bad_request
      result = HttpStatusEnum.getByCode(400)
      assertThat(result, `is`(HttpStatusEnum.BAD_REQUEST))
      assertThat(result.code, `is`(400))
      assertThat(result.description, `is`("Bad Request"))

      // unauthorized
      result = HttpStatusEnum.getByCode(401)
      assertThat(result, `is`(HttpStatusEnum.UNAUTHORIZED))
      assertThat(result.code, `is`(401))
      assertThat(result.description, `is`("Unauthorized"))

      // forbidden
      result = HttpStatusEnum.getByCode(403)
      assertThat(result, `is`(HttpStatusEnum.FORBIDDEN))
      assertThat(result.code, `is`(403))
      assertThat(result.description, `is`("Forbidden"))

      // not_found
      result = HttpStatusEnum.getByCode(404)
      assertThat(result, `is`(HttpStatusEnum.NOT_FOUND))
      assertThat(result.code, `is`(404))
      assertThat(result.description, `is`("Not Found"))

      // service_unavailable
      result = HttpStatusEnum.getByCode(503)
      assertThat(result, `is`(HttpStatusEnum.SERVICE_UNAVAILABLE))
      assertThat(result.code, `is`(503))
      assertThat(result.description, `is`("Service Unavailable"))

      // unknown_by_app
      result = HttpStatusEnum.getByCode(500)
      assertThat(result, `is`(HttpStatusEnum.UNKNOWN_BY_APP))
      assertThat(result.code, `is`(-1))
      assertThat(result.description, `is`("Unknown By The App"))
   }
}