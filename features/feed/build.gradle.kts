import dependencies.Dependencies
import extensions.implementation

plugins {
    id("commons.android-dynamic-feature")
}

dependencies {
    implementation(project(BuildModules.Features.HOME))

    implementation(Dependencies.RECYCLE_VIEW)
    implementation(Dependencies.SWIPE_REFRESH_LAYOUT)
    implementation(Dependencies.PAGING)
    implementation(Dependencies.FIREBASE_AUTH_UI)
    implementation(Dependencies.FIREBASE_AUTH)
    implementation(Dependencies.FIREBASE_DATABASE)
    implementation(Dependencies.LOTTIE)

}
