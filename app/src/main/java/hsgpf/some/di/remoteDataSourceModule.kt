package hsgpf.some.di

import hsgpf.some.model.datasource.remote.github.GithubRemoteDataS
import hsgpf.some.model.datasource.remote.retrofit.api.BASE_URL_GITHUB_API
import hsgpf.some.model.datasource.remote.retrofit.api.GithubAPI
import hsgpf.some.model.datasource.remote.retrofit.retrofitApiFactory
import org.koin.dsl.module

val remoteDataSourceModule = module {

   factory {
      GithubRemoteDataS(
         githubAPI = retrofitApiFactory(
            baseUrl = BASE_URL_GITHUB_API, clazz = GithubAPI::class.java, application = get()
         )
      )
   }
}