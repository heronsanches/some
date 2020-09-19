package hsgpf.some.view.activity.helper

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import hsgpf.some.R
import hsgpf.some.view.activity.GithubTopKotlinProjectsActivity
import hsgpf.some.view.adapter.GithubTopKotlinProjectsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class GithubTopKotlinProjectsActivityHelper(
   private val act: WeakReference<GithubTopKotlinProjectsActivity>
) {
   private lateinit var githubTopKotlinProjectsAdapter: GithubTopKotlinProjectsAdapter

   fun initializeToolbar() {
      act.get()?.run {
         binding.t.toolbar.title = getString(R.string.lbl_title_github_top_kotlin_projects)
      }
   }

   fun setupGithubTopKotlinProjectsList() {
      act.get()?.run {
         githubTopKotlinProjectsAdapter = GithubTopKotlinProjectsAdapter()
         binding.rvRepositories.layoutManager = LinearLayoutManager(this)
         binding.rvRepositories.adapter = githubTopKotlinProjectsAdapter
         repositoriesObserver()
         repositoriesErrorObserver()
      }
   }

   private fun repositoriesObserver() {
      act.get()?.run {
         lifecycleScope.launch {
            githubViewModel.repositories.collectLatest { pagingData ->
               githubTopKotlinProjectsAdapter.submitData(pagingData)
            }
         }
      }
   }

   private fun repositoriesErrorObserver() {
      act.get()?.run {
         lifecycleScope.launch {
            githubTopKotlinProjectsAdapter.loadStateFlow.collectLatest { loadStates ->
               binding.pb.isVisible = when (loadStates.refresh) {
                  LoadState.Loading -> true
                  !is LoadState.Loading -> false
               }
            }
         }
      }
   }
}