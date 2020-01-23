/**
 * Configuration of build modules
 */
object BuildModules {
    const val APP = ":app"
    const val CORE = ":core"

    object Features {
        const val SPLASH = ":features:splash"
        const val LOGIN = ":features:login"
        const val HOME = ":features:home"
        const val FEED = ":features:feed"
    }

    object Libraries {
        const val TEST_UTILS = ":libraries:test_utils"
    }

    object Commons {
        const val UI = ":commons:ui"
    }
}
