package es.chewiegames.bloggie.ui

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import es.chewiegames.bloggie.BloggieApplication
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        injectDependencies(BloggieApplication.getComponent()!!)
        setupToolbar()
        bindViews()
        initView(savedInstanceState)
    }

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract fun getLayoutId(): Int

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
        mToolbar = findViewById(R.id.toolbar)
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
        }
    }

    /**
     * Every object annotated with [butterknife.BindViews] its gonna injected trough butterknife
     */
    private fun bindViews() {
        //ButterKnife.bind(this)
    }

    /**
     * @return The toolbar that's gonna be in the activity view
     */
    @Nullable
    fun getToolbar(): Toolbar? {
        return mToolbar
    }
}