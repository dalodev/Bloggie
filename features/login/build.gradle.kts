import dependencies.Dependencies
import extensions.implementation

plugins {
    id("commons.android-dynamic-feature")
}

dependencies {
    implementation(project(BuildModules.Features.SPLASH))

    implementation(Dependencies.LOTTIE)
    implementation(Dependencies.FIREBASE_AUTH_UI)
    implementation(Dependencies.FIREBASE_AUTH)
    implementation(Dependencies.FIREBASE_DATABASE)
}
