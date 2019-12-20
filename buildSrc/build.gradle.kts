plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    jcenter()
    mavenCentral()
    maven("https://maven.fabric.io/public")
    maven("https://ci.android.com/builds/submitted/5837096/androidx_snapshot/latest/repository")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

object PluginsVersions {
    const val GRADLE_ANDROID = "3.5.3"
    const val GRADLE_VERSIONS = "0.22.0"
    const val KOTLIN = "1.3.61"
    const val NAVIGATION = "2.1.0-beta02"
    const val JACOCO = "0.16.0-SNAPSHOT"
    const val FABRIC = "1.31.2"
    const val DOKKA = "0.10.0"
    const val KTLINT = "0.34.2"
    const val SPOTLESS = "3.24.1"
    const val DETEKT = "1.0.1"
    const val GOOGLE_PLAY_SERVICES = "4.3.3"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_ANDROID}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${PluginsVersions.GRADLE_VERSIONS}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${PluginsVersions.KOTLIN}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION}")
    implementation("com.google.gms:google-services:${PluginsVersions.GOOGLE_PLAY_SERVICES}")
    implementation("io.fabric.tools:gradle:${PluginsVersions.FABRIC}")
    implementation("com.pinterest:ktlint:${PluginsVersions.KTLINT}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${PluginsVersions.DOKKA}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT}")

}
