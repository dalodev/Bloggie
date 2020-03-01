/**
 * Configuration of build modules
 */
object BuildModules {
    const val APP = ":app"
    const val DOMAIN = ":domain"
    const val CORE = ":core"

    object Features {
        const val SPLASH = ":features:splash"
        const val LOGIN = ":features:login"
        const val HOME = ":features:home"
        const val FEED = ":features:feed"
        const val NEW_POST = ":features:newpost"
    }

    object Libraries {
        const val TEST_UTILS = ":libraries:test_utils"
    }

    object Commons {
        const val UI = ":commons:ui"
    }
}
