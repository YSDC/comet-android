# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Comet
-keep class com.ysdc.comet.model.** {*; }

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn okio.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

#appsflyer
-dontwarn com.appsflyer.**

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
#

# Firebase
-keep public class com.google.firebase.iid.FirebaseInstanceId {
    public *;
}

#Moshi
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier interface *

# Enum field names are used by the integrated EnumJsonAdapter.
# Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}

# The name of @JsonClass types is used to look up the generated adapter.
-keepnames @com.squareup.moshi.JsonClass class *

# Retain generated JsonAdapters if annotated type is retained.
-keep class **JsonAdapter {
    <init>(...);
    <fields>;
}

# LeakCanary
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
#

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
#

# Play Services
-keep public class com.google.android.gms.* { public *; }
-keep public class com.google.android.gms.internal.* { *; }
-dontwarn com.google.android.gms.**
#

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keep class retrofit2.converter.gson.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
#

# RxJava
-dontwarn sun.misc.**

-keepclassmembers class test.helper.rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class test.helper.rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    test.helper.rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class test.helper.rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    test.helper.rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#

# Stheto
-keep class com.facebook.stetho.**{ *; }
#

#Prettytime
-keep class org.ocpsoft.prettytime.i18n.**



