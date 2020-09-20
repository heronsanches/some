package hsgpf.some.view.robot.githubtopkotlinprojects

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import hsgpf.some.R
import hsgpf.some.model.datasource.remote.retrofit.api.PAGINATION_SIZE
import hsgpf.some.utility.matcher.hasListSize
import hsgpf.some.utility.matcher.matchesTextOnItemPosition
import hsgpf.some.view.robot.BaseRobot

class GithubTopKotlinProjectsRobot : BaseRobot() {

   fun verifyToolbarTitle(context: Context) {
      super.verifyToolbarElements(
         navigationDescription = null,
         title = context.getString(R.string.lbl_title_github_top_kotlin_projects),
         toolbarId = R.id.t
      )
   }

   fun verifyInitialListSize() =
      matchesWithId(R.id.rvRepositories, hasListSize(PAGINATION_SIZE * 2))

   fun verifyIfListSizeExceedsTheMaximumAllowedAfterScrolls() {
      for (i in 0..PAGINATION_SIZE * 5) {
         onView(withId(R.id.rvRepositories)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(i)
         )
      }

      // verifies [PagingConfig.maxSize]
      matchesWithId(R.id.rvRepositories, hasListSize(PAGINATION_SIZE * 5))
   }

   fun verifySomeItemsContents() {
      onView(withId(R.id.rvRepositories)).perform(scrollToPosition<RecyclerView.ViewHolder>(0))
         .check(matches(matchesTextOnItemPosition("1000", 0))) // stars
      matchesWithId(R.id.rvRepositories, matchesTextOnItemPosition("999", 0)) // forks

      onView(withId(R.id.rvRepositories)).perform(scrollToPosition<RecyclerView.ViewHolder>(2))
         .check(matches(matchesTextOnItemPosition("998", 2))) // stars
      matchesWithId(R.id.rvRepositories, matchesTextOnItemPosition("997", 2)) // forks

      onView(withId(R.id.rvRepositories)).perform(scrollToPosition<RecyclerView.ViewHolder>(3))
         .check(matches(matchesTextOnItemPosition("997", 3))) // stars
      matchesWithId(R.id.rvRepositories, matchesTextOnItemPosition("996", 3)) // forks

      onView(withId(R.id.rvRepositories)).perform(scrollToPosition<RecyclerView.ViewHolder>(5))
         .check(matches(matchesTextOnItemPosition("995", 5))) // stars
      matchesWithId(R.id.rvRepositories, matchesTextOnItemPosition("994", 5)) // forks
   }
}

fun githubTopKotlinProjectsScreen(func: GithubTopKotlinProjectsRobot.() -> Unit) =
   GithubTopKotlinProjectsRobot().apply { func() }