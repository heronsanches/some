package hsgpf.some.model.datasource.remote.retrofit.data

import hsgpf.some.utils.enums.HttpStatusEnum
import okhttp3.Headers

/** [isSuccessful]: HTTP code in this range [200..300) */
data class ResponseData<T>(
   val payload: T?, val status: HttpStatusEnum, val headers: Headers,
   val isSuccessful: Boolean
)