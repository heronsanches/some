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
-keep public class * extends java.lang.Exception # Keep custom exceptions.

########################## Test application ##########################
-keep class androidx.test** { * ; }
-keep class org.koin** { * ; }
-keep class kotlin.collections** { * ; }
-keep class kotlin.coroutines.intrinsics** { * ; }