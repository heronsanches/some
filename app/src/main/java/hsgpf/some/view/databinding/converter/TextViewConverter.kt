package hsgpf.some.view.databinding.converter

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewConverter {

   @JvmStatic
   @BindingAdapter("android:text")
   fun setAsStringFromLong(textView: TextView, value: Long) {
      val newValue = value.toString()
      if (textView.text != newValue) textView.text = newValue
   }
}