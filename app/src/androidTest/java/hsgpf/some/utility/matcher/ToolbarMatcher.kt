package hsgpf.some.utility.matcher

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.size
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

fun toolbarHasElements(@StringRes navDescription: Int?, @IdRes vararg menuItemsIds: Int,
                       title: String?) =
   object : BoundedMatcher<View, Toolbar>(Toolbar::class.java) {

      override fun describeTo(description: Description?) {
         description?.appendText("Should match all elements.")
      }

      override fun matchesSafely(toolbar: Toolbar?): Boolean {
         var itsMatches = true

         toolbar?.run {
            if (title != null)
               if (this.title != title) return false

            if (navDescription != null &&
                navigationContentDescription != context.getString(navDescription))
               return false
            if (menu.size != menuItemsIds.size) return false
            val maxIndexToolbarActionItems = menu.size - 1

            // verify that all menu items of the Toolbar have a correspondent on menuItemsIds
            for (i in 0..maxIndexToolbarActionItems) {
               if (menuItemsIds.find { menu.findItem(it) != null } == null) {
                  itsMatches = false
                  break
               }
            }
         }
         return itsMatches
      }
   }

