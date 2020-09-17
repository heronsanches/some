package hsgpf.some.di

import hsgpf.some.model.repository.remote.github.GithubRemoteR
import org.koin.dsl.module

val remoteRepositoryModule = module {
   factory { GithubRemoteR(githubRemoteDataSource = get()) }
}