@file:Suppress("KotlinDeprecation")

package hsgpf.some.di

import hsgpf.some.model.repository.remote.github.GithubRemoteR
import hsgpf.some.model.repository.remote.github.GithubRemoteRepository
import org.koin.dsl.module

val remoteRepositoryModule = module {
   factory { GithubRemoteR(githubRemotePagedDataSource = get()) as GithubRemoteRepository }
}