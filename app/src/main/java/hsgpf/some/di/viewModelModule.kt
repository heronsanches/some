package hsgpf.some.di

import hsgpf.some.viewModel.GithubTopKotlinProjectsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { GithubTopKotlinProjectsViewModel(githubRemoteRepository = get()) }
}