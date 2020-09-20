package hsgpf.some.view.robot

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import hsgpf.some.R
import hsgpf.some.utility.matcher.toolbarHasElements
import org.hamcrest.Matcher

abstract class BaseRobot {

   fun matchesWithId(@IdRes viewResourceId: Int, matcher: Matcher<View>) {
      onView(withId(viewResourceId)).check(matches(matcher))
   }

   fun verifyToolbarElements(@StringRes navigationDescription: Int?, @IdRes vararg menuItems: Int,
                             title: String? = null, @IdRes toolbarId: Int?) {
      val id = toolbarId ?: R.id.toolbar
      matchesWithId(id, toolbarHasElements(navigationDescription, *menuItems,
                                           title = title))
   }
}