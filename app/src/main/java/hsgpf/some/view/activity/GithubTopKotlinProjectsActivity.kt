package hsgpf.some.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hsgpf.some.view.activity.helper.GithubTopKotlinProjectsActivityHelper
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GithubTopKotlinProjectsActivity : AppCompatActivity() {
   private val helper: GithubTopKotlinProjectsActivityHelper by inject { parametersOf(this) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
   }
}