package hsgpf.some.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hsgpf.some.R
import hsgpf.some.databinding.ActivityGithubTopKotlinProjectsBinding
import hsgpf.some.view.activity.helper.GithubTopKotlinProjectsActivityHelper
import hsgpf.some.viewModel.GithubTopKotlinProjectsViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GithubTopKotlinProjectsActivity : AppCompatActivity() {
   private val helper: GithubTopKotlinProjectsActivityHelper by inject { parametersOf(this) }
   val githubTopKotlinProjectsViewModel: GithubTopKotlinProjectsViewModel by viewModel()
   val SAVED_NEXT_PAGE = "SAVED_NEXT_PAGE"
   val SAVED_ACTUAL_PAGE = "SAVED_ACTUAL_PAGE"

   val binding: ActivityGithubTopKotlinProjectsBinding by lazy {
      DataBindingUtil.setContentView(this, R.layout.activity_github_top_kotlin_projects)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      helper.initializeToolbar()
      helper.setupGithubTopKotlinProjectsList(savedInstanceState)
   }

   override fun onSaveInstanceState(outState: Bundle) {
      outState.putInt(SAVED_NEXT_PAGE, helper.nextPage)
      outState.putInt(SAVED_ACTUAL_PAGE, helper.actualPage)
      super.onSaveInstanceState(outState)
   }
}