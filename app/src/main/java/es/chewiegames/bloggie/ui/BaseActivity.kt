package es.chewiegames.bloggie.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import es.chewiegames.bloggie.BloggieApplication
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutId())
        injectDependencies(BloggieApplication.getComponent()!!)
        setupToolbar()
        initView(savedInstanceState)
    }

    /**
     * This method implements in every activity to inject the dependences
     * @param component ApplicationComponent reference to inject the dependences
     */
    protected abstract fun injectDependencies(component: ApplicationComponent)

    /**
     * Use this method to initialize view components. This method is called after [ ][BaseActivity.bindViews]
     */
    open fun initView(savedInstanceState: Bundle?) {}

    /**
     * Its common use a toolbar within activity, if it exists in the
     * layout this will be configured
     */
    fun setupToolbar() {
        //mToolbar = findViewById(R.id.toolbar)
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
        }
    }

    /**
     * @return The toolbar that's gonna be in the activity view
     */
    @Nullable
    fun getToolbar(): Toolbar? {
        return mToolbar
    }

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract fun getLayoutId(): Int
}