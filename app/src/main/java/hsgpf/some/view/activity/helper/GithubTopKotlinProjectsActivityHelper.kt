package hsgpf.some.view.activity.helper

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hsgpf.some.R
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoriesData
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData
import hsgpf.some.view.activity.GithubTopKotlinProjectsActivity
import hsgpf.some.view.adapter.GithubTopKotlinProjectsAdapter
import java.lang.ref.WeakReference

class GithubTopKotlinProjectsActivityHelper(
   private val act: WeakReference<GithubTopKotlinProjectsActivity>
) {
   private lateinit var githubTopKotlinProjectsAdapter: GithubTopKotlinProjectsAdapter
   private lateinit var linearLayoutManager: LinearLayoutManager
   private var scrollingUp = false

   fun initializeToolbar() {
      act.get()?.run {
         binding.t.toolbar.title = getString(R.string.lbl_title_github_top_kotlin_projects)
      }
   }

   fun setupGithubTopKotlinProjectsList() {

      act.get()?.run {
         linearLayoutManager = LinearLayoutManager(this)
         githubTopKotlinProjectsAdapter = GithubTopKotlinProjectsAdapter()
         binding.rvRepositories.layoutManager = linearLayoutManager
         binding.rvRepositories.adapter = githubTopKotlinProjectsAdapter
         binding.rvRepositories.addOnScrollListener(rvRepositoriesScrollListener)
         repositoriesObserver()
         loadingRepositoriesObserver()

         if (githubViewModel.repositories().value == null)
            githubViewModel.searchRepositories(1)
      }
   }

   private fun loadingRepositoriesObserver() {
      act.get()?.run {
         githubViewModel.loadingRepositories().observe(this, { isLoading ->
            binding.pb.isVisible = isLoading
         })
      }
   }

   private val rvRepositoriesScrollListener = object : RecyclerView.OnScrollListener() {

      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
         act.get()?.run {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
               val hasItemsDown = recyclerView.canScrollVertically(1)
               val hasItemsUp = recyclerView.canScrollVertically(-1)

               if (!hasItemsDown) {
                  scrollingUp = false
                  githubViewModel.searchRepositories(githubViewModel.nextPage)
               } else if (!hasItemsUp && githubViewModel.actualPage > 1) {
                  scrollingUp = true
                  githubViewModel.searchRepositories(githubViewModel.actualPage - 1)
               }
            }
         }
      }
   }

   private fun repositoriesObserver() {
      act.get()?.run {
         githubViewModel.repositories().observe(this, { repositories ->
            val infiniteList = mutableListOf<GithubRepositoryData>()

            if (scrollingUp) defineScrollUpInfiniteList(repositories, infiniteList)
            else {
               if (githubViewModel.actualPage > 1 && !githubViewModel.onSavedInstance)
                  defineScrollDownInfiniteList(repositories, infiniteList)
               else { // activity recreated
                  infiniteList.addAll(repositories.items)
               }
            }
            githubViewModel.nextPage = repositories.nextPage
            githubViewModel.actualPage = repositories.actualPage
            githubViewModel.onSavedInstance = false
            githubTopKotlinProjectsAdapter.submitList(infiniteList)
         })
      }
   }

   private fun addVisibleItems(infiniteList: MutableList<GithubRepositoryData>) {
      val startIndex = linearLayoutManager.findFirstVisibleItemPosition()
      val endIndex = linearLayoutManager.findLastVisibleItemPosition() + 1

      infiniteList.addAll(
         githubTopKotlinProjectsAdapter.currentList.subList(startIndex, endIndex)
      )
   }

   private fun defineScrollUpInfiniteList(repositories: GithubRepositoriesData,
                                          infiniteList: MutableList<GithubRepositoryData>) {
      infiniteList.addAll(repositories.items)
      addVisibleItems(infiniteList)
   }

   private fun defineScrollDownInfiniteList(repositories: GithubRepositoriesData,
                                            infiniteList: MutableList<GithubRepositoryData>) {
      addVisibleItems(infiniteList)
      infiniteList.addAll(repositories.items)
   }
}