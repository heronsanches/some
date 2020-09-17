package hsgpf.some.di

import hsgpf.some.view.activity.GithubTopKotlinProjectsActivity
import hsgpf.some.view.activity.helper.GithubTopKotlinProjectsActivityHelper
import org.koin.dsl.module
import java.lang.ref.WeakReference

val activityHelperModule = module {
   factory { (activity: GithubTopKotlinProjectsActivity) ->
      GithubTopKotlinProjectsActivityHelper(WeakReference(activity))
   }
}