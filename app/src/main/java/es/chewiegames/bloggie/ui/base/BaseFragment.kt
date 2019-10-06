package es.chewiegames.bloggie.ui.base

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import es.chewiegames.bloggie.di.component.ApplicationComponent

abstract class BaseFragment : Fragment() {

    protected var loaderCallback: OnLoaderCallback? = null

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(getLayoutId(), container, false)
        bindViews(inflater, container, getLayoutId())
        initObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    open fun bindViews(inflater: LayoutInflater, container: ViewGroup?, layoutId: Int) {}

    /**
     * Start lifecycle  methods
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoaderCallback) {
            loaderCallback = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        loaderCallback = null
    }

    /**
     * init viewmodel observers
     */
    open fun initObservers(){}
    abstract fun injectDependencies(component: ApplicationComponent, context: Context)
    /**
     * @return The layout id that's gonna be the fragment view.
     */
    protected abstract fun getLayoutId(): Int

    /**
     * Use this method to initialize view components. This method is called after [ ][BaseFragment.bindViews] ()}
     */
    open fun initView() {}

    /**
     * call destroy view lifecycle
     */
//    protected abstract fun destroyView()


    /**
     * Fragment navigation.
     */
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    open fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{
            replace(frameId, fragment, fragment.javaClass.simpleName)
            addToBackStack(fragment.javaClass.simpleName)
        }
    }

    /**
     * Start callbacks
     */
    interface OnLoaderCallback {
        fun onLoading()
        fun onFinishLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
//        destroyView()
    }
}