/*
 * Copyright 2019 littledavity
 */
import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies
import extensions.implementation
import extensions.kapt

plugins {
    id("commons.android-library")
}

junitJacoco {
    excludes = listOf("**/extensions/NavigationExtensions*.*")
}

dependencies {
    implementation(Dependencies.LIFECYCLE_EXTENSIONS)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.CONSTRAIN_LAYOUT)
    implementation(Dependencies.RECYCLE_VIEW)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.PAGING)
    implementation(Dependencies.COIL)

    kapt(AnnotationProcessorsDependencies.DATABINDING)
}
