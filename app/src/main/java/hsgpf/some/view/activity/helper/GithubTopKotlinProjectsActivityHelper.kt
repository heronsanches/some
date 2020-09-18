package hsgpf.some.view.activity.helper

import android.os.Bundle
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

   var nextPage: Int = 1
      private set

   var actualPage: Int = 1
      private set

   private var scrollingUp = false

   fun initializeToolbar() {
      act.get()?.run {
         binding.t.toolbar.title = getString(R.string.lbl_title_github_top_kotlin_projects)
      }
   }

   fun setupGithubTopKotlinProjectsList(savedInstanceState: Bundle?) {

      act.get()?.run {
         savedInstanceState?.run {
            nextPage = savedInstanceState.getInt(SAVED_NEXT_PAGE, 1)
            actualPage = savedInstanceState.getInt(SAVED_ACTUAL_PAGE, 1)
         }
         linearLayoutManager = LinearLayoutManager(this)
         githubTopKotlinProjectsAdapter = GithubTopKotlinProjectsAdapter()
         binding.rvRepositories.layoutManager = linearLayoutManager
         binding.rvRepositories.adapter = githubTopKotlinProjectsAdapter
         binding.rvRepositories.addOnScrollListener(rvRepositoriesScrollListener)
         repositoriesObserver()
         loadingRepositoriesObserver()

         if (githubTopKotlinProjectsViewModel.repositories().value == null)
            githubTopKotlinProjectsViewModel.searchRepositories(actualPage)
      }
   }

   private fun loadingRepositoriesObserver() {
      act.get()?.run {
         githubTopKotlinProjectsViewModel.loadingRepositories().observe(this, { isLoading ->
            binding.pb.isVisible = isLoading
         })
      }
   }

   private val rvRepositoriesScrollListener = object : RecyclerView.OnScrollListener() {

      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
         act.get()?.run {
            if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
               val hasItemsDown = recyclerView.canScrollVertically(1)
               val hasItemsUp = recyclerView.canScrollVertically(-1)

               if (!hasItemsDown) {
                  scrollingUp = false
                  githubTopKotlinProjectsViewModel.searchRepositories(nextPage)
               } else if (!hasItemsUp && actualPage > 1) {
                  scrollingUp = true
                  githubTopKotlinProjectsViewModel.searchRepositories(actualPage - 1)
               }
            }
         }
      }
   }

   private fun repositoriesObserver() {
      act.get()?.run {
         githubTopKotlinProjectsViewModel.repositories().observe(this, { repositories ->
            actualPage = repositories.actualPage
            nextPage = repositories.nextPage
            val infiniteList = mutableListOf<GithubRepositoryData>()

            if (scrollingUp) defineScrollUpInfiniteList(repositories, infiniteList)
            else {
               if (actualPage > 1) defineScrollDownInfiniteList(repositories, infiniteList)
               else infiniteList.addAll(repositories.items)
            }
            githubTopKotlinProjectsAdapter.submitList(infiniteList)
         })
      }
   }

   private fun addVisibleItems(infiniteList: MutableList<GithubRepositoryData>) {
      val startIndex = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
      val endIndex = linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1
      infiniteList.addAll(githubTopKotlinProjectsAdapter.currentList.subList(startIndex, endIndex))
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