package dependencies

import BuildDependenciesVersions

/**
 * Project dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object Dependencies {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${BuildDependenciesVersions.KOTLIN}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${BuildDependenciesVersions.APPCOMPAT}"
    const val MATERIAL = "com.google.android.material:material:${BuildDependenciesVersions.MATERIAL}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildDependenciesVersions.COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildDependenciesVersions.COROUTINES}"
    const val RECYCLE_VIEW = "androidx.recyclerview:recyclerview:${BuildDependenciesVersions.RECYCLE_VIEW}"
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${BuildDependenciesVersions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    const val CORE_KTX = "androidx.core:core-ktx:${BuildDependenciesVersions.CORE_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${BuildDependenciesVersions.FRAGMENT_KTX}"
    const val CONSTRAIN_LAYOUT = "androidx.constraintlayout:constraintlayout:${BuildDependenciesVersions.CONSTRAIN_LAYOUT}"
    const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:${BuildDependenciesVersions.SWIPE_REFRESH_LAYOUT}"
    const val PAGING = "androidx.paging:paging-runtime-ktx:${BuildDependenciesVersions.PAGING}"
    const val TIMBER = "com.jakewharton.timber:timber:${BuildDependenciesVersions.TIMBER}"
    const val COIL = "io.coil-kt:coil:${BuildDependenciesVersions.COIL}"
    const val CRASHLYTICS = "com.crashlytics.sdk.android:crashlytics:${BuildDependenciesVersions.CRASHLYTICS}"
    const val FIREBASE_ANALIYTICS = "com.google.firebase:firebase-analytics:${BuildDependenciesVersions.FIREBASE_ANALYTICS}"
    const val PLAY_CORE = "com.google.android.play:core:${BuildDependenciesVersions.PLAY_CORE}"
    const val FIREBASE_CORE = "com.google.firebase:firebase-core:${BuildDependenciesVersions.FIREBASE_CORE}"
    const val FIREBASE_AUTH_PLAY_SERVICES = "com.google.android.gms:play-services-auth:${BuildDependenciesVersions.FIREBASE_AUTH_PLAY_SERVICES}"
    const val FIREBASE_AUTH_UI = "com.firebaseui:firebase-ui-auth:${BuildDependenciesVersions.FIREBASE_AUTH_UI}"
    const val FIREBASE_AUTH = "com.google.firebase:firebase-auth:${BuildDependenciesVersions.FIREBASE_AUTH}"
    const val FIREBASE_DATABASE = "com.google.firebase:firebase-database:${BuildDependenciesVersions.FIREBASE_DATABASE}"
    const val FIREBASE_STORAGE = "com.google.firebase:firebase-storage:${BuildDependenciesVersions.FIREBASE_STORAGE}"
    const val PICASSO = "com.squareup.picasso:picasso:${BuildDependenciesVersions.PICASSO}"
    const val LOTTIE = "com.airbnb.android:lottie:${BuildDependenciesVersions.LOTTIE}"
    const val KOIN = "org.koin:koin-android:${BuildDependenciesVersions.KOIN}"
    const val KOIN_VIEWMODEL = "org.koin:koin-androidx-viewmodel:${BuildDependenciesVersions.KOIN}"
    const val ANNOTATIONS = "androidx.annotation:annotation:${BuildDependenciesVersions.ANNOTATIONS}"
}