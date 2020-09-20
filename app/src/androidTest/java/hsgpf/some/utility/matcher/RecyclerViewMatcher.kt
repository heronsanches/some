package hsgpf.some.utility.matcher

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

fun hasListSize(size: Int) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

   override fun describeTo(description: Description?) {
      description?.appendText("the size list should be: $size")
   }

   override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
      return recyclerView?.adapter?.itemCount == size
   }
}

/** Before use it, call the following espresso function:
 * onView(withId(viewId)).perform(scrollToPosition<RecyclerView.ViewHolder>(elementPosition)) */
fun matchesTextOnItemPosition(text: String, position: Int) =
   object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

      override fun describeTo(description: Description?) {
         description?.appendText("should have '$text' on position '$position'")
      }

      override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
         var isMatch = false
         val itemView = recyclerView?.findViewHolderForAdapterPosition(position)?.itemView
         itemView?.run { isMatch = searchTextOnPosition(this, text) }
         return isMatch
      }
   }

private fun searchTextOnPosition(itemView: View, text: String, result: Boolean = false): Boolean {
   if (result) return true
   var recursionResult = result
   if (itemView is TextView && itemView.text.toString().equals(text, ignoreCase = true)) return true

   if (itemView is ViewGroup) {
      val maxChildIndex = itemView.childCount - 1

      for (i in 0..maxChildIndex) recursionResult =
         searchTextOnPosition(itemView.getChildAt(i), text, recursionResult)
   }
   return recursionResult
}