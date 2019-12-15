import dependencies.AnnotationProcessorsDependencies
import dependencies.Dependencies

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(project(BuildModules.DATA))

    implementation(Dependencies.FIREBASE_AUTH_PLAY_SERVICES)
    implementation(Dependencies.FIREBASE_AUTH_UI)
    implementation(Dependencies.FIREBASE_AUTH)
}
