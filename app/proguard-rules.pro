########################## Glide ##########################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule

-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
##########################
-keep class hsgpf.some** { *; }
-keep class androidx.databinding** { * ; }
-keep class androidx.drawerlayout.widget** { * ; }
-keep class androidx.recyclerview.widget.RecyclerView { * ; }
-keep class com.google.gson** { * ; }
-keep class kotlin.collections** { * ; }
-keep class kotlin.coroutines.intrinsics** { * ; }
-keep class kotlin.jvm.internal.SpreadBuilder { * ; }
-keep class kotlin.LazyKt
-keep class kotlin.sequences** { * ; }
-keep class kotlin.text.StringsKt { * ; }
-keep class kotlinx.coroutines** { * ; }
-keep class org.koin** { * ; }
-keep public class * extends java.lang.Exception # Keep custom exceptions.