package es.littledavity.bloggie.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import es.littledavity.bloggie.R

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    private var mToolbar: Toolbar? = null
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(getLayoutId())
        setupToolbar()
        initView(savedInstanceState)
        initObservers()
    }

    /**
     * Binding the view with layout resource
     */
    protected fun bindView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    /**
     * Navigation jetpack navigation support
     */
    override fun onSupportNavigateUp() = findNavController(R.id.my_nav_host_fragment).navigateUp()

    /**
     * init viewmodel observers
     */
    open fun initObservers() {}

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract fun getLayoutId(): Int

    /**
     * call destroy view lifecycle
     */
//    protected abstract fun destroyView()

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

    override fun onDestroy() {
        super.onDestroy()
//        destroyView()
    }

    /**
     * @return The toolbar that's gonna be in the activity view
     */
    @Nullable
    fun getToolbar(): Toolbar? {
        return mToolbar
    }

    /**
     * Navigation configuration
     */
    fun getNavController(): NavController {
        val host: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        return host.navController
    }

    /**
     * Fragment navigation.
     */
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    open fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction {
            replace(frameId, fragment, fragment.javaClass.simpleName)
            addToBackStack(fragment.javaClass.simpleName)
        }
    }

    fun <T> AppCompatActivity.getFragment(fragmentClass: Class<T>) =
            supportFragmentManager.findFragmentByTag(fragmentClass.simpleName)

    /**
     * Activity navigation.
     */
    fun AppCompatActivity.goToActivity(activity: Activity, activityToFinish: Activity) {
        val intent = Intent(this, activity::class.java)
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        startActivity(intent)
        activityToFinish.finish()
    }
}
