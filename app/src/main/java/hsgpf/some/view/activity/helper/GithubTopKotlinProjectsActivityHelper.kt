package hsgpf.some.view.activity.helper

import androidx.recyclerview.widget.LinearLayoutManager
import hsgpf.some.R
import hsgpf.some.view.activity.GithubTopKotlinProjectsActivity
import hsgpf.some.view.adapter.GithubTopKotlinProjectsAdapter
import java.lang.ref.WeakReference

class GithubTopKotlinProjectsActivityHelper(
   private val act: WeakReference<GithubTopKotlinProjectsActivity>
) {
   private var githubTopKotlinProjectsAdapter: GithubTopKotlinProjectsAdapter? = null

   fun initializeToolbar() {
      act.get()?.run {
         binding.t.toolbar.title =
            getString(R.string.lbl_title_github_top_kotlin_projects)
      }
   }

   fun setupGithubTopKotlinProjectsList() {
      act.get()?.run {
         repositoriesObserver()
         githubTopKotlinProjectsAdapter = GithubTopKotlinProjectsAdapter()
         binding.rvRepositories.layoutManager = LinearLayoutManager(this)
         binding.rvRepositories.adapter = githubTopKotlinProjectsAdapter

         if (githubTopKotlinProjectsViewModel.repositories().value == null)
            githubTopKotlinProjectsViewModel.searchRepositoriesInitialPage()
      }
   }

   private fun repositoriesObserver() {
      act.get()?.run {
         githubTopKotlinProjectsViewModel.repositories().observe(this, { repositories ->
            githubTopKotlinProjectsAdapter?.submitList(repositories.items)
         })
      }
   }
}