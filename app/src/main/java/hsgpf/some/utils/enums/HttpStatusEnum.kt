package hsgpf.some.utils.enums

// TODO: only use the codes treated by the app
enum class HttpStatusEnum(val code: Int, val description: String) {
   OK(200, "OK"), CREATED(201, "Created"), PERMANENT_REDIRECTED(308, "Permanent Redirect"),
   BAD_REQUEST(400, "Bad Request"), UNAUTHORIZED(401, "Unauthorized"),
   FORBIDDEN(403, "Forbidden"), NOT_FOUND(404, "Not Found"),
   SERVICE_UNAVAILABLE(503, "Service Unavailable"), UNKNOWN_BY_APP(-1, "Unknown By The App");

   companion object {
      fun getByCode(code: Int) = values().find { it.code == code } ?: UNKNOWN_BY_APP
   }
}