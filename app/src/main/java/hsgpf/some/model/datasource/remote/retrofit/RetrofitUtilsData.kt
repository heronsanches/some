package hsgpf.some.model.datasource.remote.retrofit

import android.app.Application
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter

data class RetrofitUtilsData<T>(
   val baseUrl: String, val converterFactory: List<Converter.Factory> = listOf(),
   val clazz: Class<T>,
   val loggingLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,
   val readTimeout: Long = 15L, val writeTimeout: Long = 15L, val application: Application,
   val cacheSizeInBytes: Long = 0, val cacheName: String = "", val cacheMaxAgeInSeconds: Int = 0
)