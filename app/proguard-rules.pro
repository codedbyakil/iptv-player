# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android.txt

# Keep data classes
-keep class com.streamflix.iptv.data.** { *; }

# Keep ExoPlayer
-keep class androidx.media3.** { *; }

# Keep Coil
-keep class coil.** { *; }
-dontwarn coil.**

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
