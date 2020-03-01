/*
 * Copyright 2020 littledavity
 */
import dependencies.Dependencies
import extensions.implementation

plugins {
    id("commons.android-dynamic-feature")
}

dependencies {
    implementation(project(BuildModules.Features.LOGIN))
    implementation(project(BuildModules.Features.SPLASH))

    implementation(Dependencies.LOTTIE)
}
