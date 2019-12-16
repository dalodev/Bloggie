// Top-level build file where you can add configuration options common to all sub-projects/modules.

import extensions.applyDefault

plugins.apply(BuildPlugins.GIT_HOOKS)

allprojects {
    repositories.applyDefault()

    plugins.apply(BuildPlugins.KTLINT)
}
