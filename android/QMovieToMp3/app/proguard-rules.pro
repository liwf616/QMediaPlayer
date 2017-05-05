# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yuqilin/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#指定压缩级别
-optimizationpasses 5
-keepattributes SourceFile,LineNumberTable

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses

# ijkplayer
-dontwarn tv.danmaku.ijk.media.player.**
-keep public class tv.danmaku.ijk.media.player.** {*;}
-keep public interface tv.danmaku.ijk.media.player.** {*;}

-keep public class com.github.yuqilin.qmediaplayer.** { *; }
-keep public class com.github.yuqilin.qmediaplayerapp.gui.** { *; }

#-keep class com.facebook.ads.** { *; }
-dontwarn com.googlecode.mp4parser.**

#-keep class com.av.ringtone.model.** { *; }

# 第三方包 jl1.0.1.jar
-dontwarn javazoom.jl.**
-keep class javazoom.jl.** { *;}

