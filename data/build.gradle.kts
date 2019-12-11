import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(Dependencies.FIREBASE_AUTH_PLAY_SERVICES)
    implementation(Dependencies.FIREBASE_AUTH_UI)
    implementation(Dependencies.FIREBASE_AUTH)
    implementation(Dependencies.FIREBASE_DATABASE)
    implementation(Dependencies.FIREBASE_STORAGE)
    implementation(Dependencies.FIREBASE_CORE)
    implementation(Dependencies.CORE_KTX)
}
