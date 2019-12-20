// Top-level build file where you can add configuration options common to all sub-projects/modules.

import extensions.applyDefault

plugins.apply(BuildPlugins.GIT_HOOKS)
plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)

allprojects {
    repositories.applyDefault()

    plugins.apply(BuildPlugins.KTLINT)
    plugins.apply(BuildPlugins.DETEKT)
}
