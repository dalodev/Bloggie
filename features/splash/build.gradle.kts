/*
 * Copyright 2020 littledavity
 */
import dependencies.Dependencies
import extensions.implementation

plugins {
    id("commons.android-dynamic-feature")
}

dependencies {
    implementation(Dependencies.LOTTIE)
    implementation(Dependencies.FIREBASE_AUTH)
    implementation(Dependencies.FIREBASE_DATABASE)
}
