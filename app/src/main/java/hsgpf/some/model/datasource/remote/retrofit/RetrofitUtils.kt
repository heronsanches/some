package hsgpf.some.model.datasource.remote.retrofit

import com.google.gson.GsonBuilder
import hsgpf.some.BuildConfig
import hsgpf.some.model.datasource.remote.retrofit.data.ResponseData
import hsgpf.some.utils.enums.HttpStatusEnum
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

/** It creates a Retrofit API based on parameters passed, the parameters are passed by
 *  [RetrofitUtilsData]. By definition it always is going to add these following converters,
 *  in this exactly order: [ScalarsConverterFactory] and [GsonConverterFactory].
 *  If you want to add some other converters pass it throw [RetrofitUtilsData.converterFactory]
 *  parameter, so these converters will take precedence from the others.
 *  The [RetrofitUtilsData.loggingLevel] is only applied on [BuildConfig.BUILD_TYPE] different
 *  from "release". The [RetrofitUtilsData.baseUrl] parameter must end with "/".*/
fun <T> retrofitApiFactory(data: RetrofitUtilsData<T>): T {
   val logging =
      if (BuildConfig.BUILD_TYPE != "release") HttpLoggingInterceptor().setLevel(data.loggingLevel)
      else null

   val cache =
      if (data.cacheSizeInBytes > 0)
         Cache(File(data.application.cacheDir, data.cacheName), data.cacheSizeInBytes)
      else null

   val okHttpClient = OkHttpClient.Builder().apply {
      readTimeout(data.readTimeout, TimeUnit.SECONDS)
      writeTimeout(data.writeTimeout, TimeUnit.SECONDS)
      cache?.let { this.cache(cache) }
      logging?.run { addInterceptor(this) }
      cache?.let { addNetworkInterceptor(CacheInterceptor(data.cacheMaxAgeInSeconds)) }
   }.build()

   return Retrofit.Builder().apply {
      baseUrl(data.baseUrl)
      data.converterFactory.forEach { addConverterFactory(it) }
      addConverterFactory(ScalarsConverterFactory.create())
      addConverterFactory(GsonConverterFactory.create(gsonDefault()))
      client(okHttpClient)
   }.build().create(data.clazz)
}

/** It transform the retrofit [Response] into a [ResponseData]. */
fun <T> processResponse(response: Response<T>): ResponseData<T> {
   val bodyResult = response.body()
   val resultCode = HttpStatusEnum.getByCode(response.code())
   val headers = response.headers()
   return ResponseData(bodyResult, resultCode, headers, response.isSuccessful)
}