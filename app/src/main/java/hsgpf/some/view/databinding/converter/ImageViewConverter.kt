package hsgpf.some.view.databinding.converter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import hsgpf.some.R

object ImageViewConverter {

   @BindingAdapter("imageUrl")
   fun setAsDrawableFromString(imageView: ImageView, url: String) {
      if (url.isNotBlank()) Glide.with(imageView.context).load(url).into(imageView)
      else Glide.with(imageView.context).load(R.drawable.ic_no_image).into(imageView)
   }
}