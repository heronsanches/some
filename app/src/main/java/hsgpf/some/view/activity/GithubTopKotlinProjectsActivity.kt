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
   val githubViewModel: GithubTopKotlinProjectsViewModel by viewModel()

   val binding: ActivityGithubTopKotlinProjectsBinding by lazy {
      DataBindingUtil.setContentView(this, R.layout.activity_github_top_kotlin_projects)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      helper.initializeToolbar()
      helper.setupGithubTopKotlinProjectsList()
   }

   override fun onSaveInstanceState(outState: Bundle) {
      githubViewModel.onSavedInstance = true
      super.onSaveInstanceState(outState)
   }
}