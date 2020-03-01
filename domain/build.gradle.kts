/*
 * Copyright 2020 littledavity
 */
import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies
import extensions.implementation
import extensions.kapt
import extensions.getLocalProperty
import extensions.buildConfigBooleanField
import extensions.buildConfigIntField
import extensions.buildConfigStringField

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(project(BuildModules.CORE))
}
