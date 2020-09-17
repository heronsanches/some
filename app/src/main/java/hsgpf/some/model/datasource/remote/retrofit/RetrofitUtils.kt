package hsgpf.some.model.datasource.remote.retrofit

import android.app.Application
import com.google.gson.GsonBuilder
import hsgpf.some.BuildConfig
import hsgpf.some.model.datasource.remote.retrofit.data.ResponseData
import hsgpf.some.utils.enums.HttpStatusEnum
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private fun gsonDefault() = GsonBuilder().create()

private class CacheInterceptor(private val cacheMaxAge: Int) : Interceptor {

   override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
      val originalResponse = chain.proceed(chain.request())
      return originalResponse.newBuilder().header("Cache-Control", "max-age=$cacheMaxAge").build()
   }
}

// TODO: create a Data for retrofit factory parameters
/** It creates a Retrofit API based on parameters passed. By definition it always is going to
 * add these following converters, in this exaclty order: [ScalarsConverterFactory] and
 * [GsonConverterFactory]. If you want to add some other converters pass it throw
 * [converterFactory] parameter, so these converters will take precedence from the others.
 * The [loggingLevel] is only applied on [BuildConfig.BUILD_TYPE] different from "release".
 * The [baseUrl] parameter must end with "/".*/
fun <T> retrofitApiFactory(
   baseUrl: String, vararg converterFactory: Converter.Factory = arrayOf(), clazz: Class<T>,
   loggingLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,
   readTimeout: Long = 15L, writeTimeout: Long = 15L, application: Application,
   cacheSizeInBytes: Long = 0, cacheName: String = "", cacheMaxAgeInSeconds: Int = 0
): T {
   val logging =
      if (BuildConfig.BUILD_TYPE != "release") HttpLoggingInterceptor().setLevel(loggingLevel)
      else null

   val cache =
      if (cacheSizeInBytes > 0) Cache(File(application.cacheDir, cacheName), cacheSizeInBytes)
      else null

   val okHttpClient = OkHttpClient.Builder().apply {
      readTimeout(readTimeout, TimeUnit.SECONDS)
      writeTimeout(writeTimeout, TimeUnit.SECONDS)
      cache?.let { this.cache(cache) }
      logging?.run { addInterceptor(this) }
      cache?.let { addNetworkInterceptor(CacheInterceptor(cacheMaxAgeInSeconds)) }
   }.build()

   return Retrofit.Builder().apply {
      baseUrl(baseUrl)
      converterFactory.forEach { addConverterFactory(it) }
      addConverterFactory(ScalarsConverterFactory.create())
      addConverterFactory(GsonConverterFactory.create(gsonDefault()))
      client(okHttpClient)
   }.build().create(clazz)
}

/** It transform the retrofit [Response] into a [ResponseData]. */
fun <T> processResponse(response: Response<T>): ResponseData<T> {
   val bodyResult = response.body()
   val resultCode = HttpStatusEnum.getByCode(response.code())
   val headers = response.headers()
   return ResponseData(bodyResult, resultCode, headers, response.isSuccessful)
}