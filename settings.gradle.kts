/*
 * Copyright 2019 littledavity
 */
include(":features:home")
include(":features:login")
include(
    BuildModules.APP,
    BuildModules.CORE,
    BuildModules.Features.SPLASH,
    BuildModules.Features.LOGIN,
    BuildModules.Libraries.TEST_UTILS,
    BuildModules.Commons.UI
)

rootProject.buildFileName = "build.gradle.kts"
