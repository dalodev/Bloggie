/**
 * Configuration of build modules
 */
object BuildModules {
    const val APP = ":app"
    const val DOMAIN = ":domain"
    const val DATA = ":data"

    object Libraries {
        const val TEST_UTILS = ":libraries:test_utils"
    }

    object Commons {
        const val UI = ":commons:ui"
        const val VIEWS = ":commons:views"
    }
}
