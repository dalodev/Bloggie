/*
 * Copyright 2019 littledavity
 */
import dependencies.Dependencies
import dependencies.TestDependencies
import dependencies.AnnotationProcessorsDependencies
import extensions.implementation

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(Dependencies.PAGING)
    implementation(Dependencies.NAVIGATION_UI)

    implementation(TestDependencies.MOCKITO)
    implementation(TestDependencies.ASSERTJ)
    implementation(TestDependencies.ROBOELECTRIC)
    implementation(TestDependencies.CORE)
    implementation(TestDependencies.ARCH_CORE)
    implementation(TestDependencies.RULES)
    implementation(TestDependencies.RUNNER)
    implementation(TestDependencies.COROUTINES_TEST)
    implementation(TestDependencies.FRAGMENT_TEST)
    implementation(TestDependencies.EXT)
    implementation(TestDependencies.MOCK_WEB_SERVER)

    kapt(AnnotationProcessorsDependencies.AUTO_SERVICE)
    annotationProcessor(AnnotationProcessorsDependencies.AUTO_SERVICE)
}
