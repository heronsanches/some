@file:Suppress("KotlinDeprecation")

package hsgpf.some.di

import hsgpf.some.model.datasource.remote.github.GithubRemoteDataS
import hsgpf.some.model.datasource.remote.github.GithubRemoteDataSource
import hsgpf.some.model.datasource.remote.retrofit.RetrofitUtilsData
import hsgpf.some.model.datasource.remote.retrofit.api.BASE_URL_GITHUB_API
import hsgpf.some.model.datasource.remote.retrofit.api.CACHE_NAME_GITHUB_API
import hsgpf.some.model.datasource.remote.retrofit.api.GithubAPI
import hsgpf.some.model.datasource.remote.retrofit.retrofitApiFactory
import org.koin.dsl.module

val remoteDataSourceModule = module {

   factory {
      val retrofitUtilsData = RetrofitUtilsData(
         baseUrl = BASE_URL_GITHUB_API, clazz = GithubAPI::class.java, application = get(),
         cacheSizeInBytes = 20971520, cacheMaxAgeInSeconds = 600,
         cacheName = CACHE_NAME_GITHUB_API
      )
      GithubRemoteDataS(retrofitApiFactory(retrofitUtilsData)) as GithubRemoteDataSource
   }
}