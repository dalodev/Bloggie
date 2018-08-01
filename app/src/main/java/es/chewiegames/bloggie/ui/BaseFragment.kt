package es.chewiegames.bloggie.ui

import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.chewiegames.bloggie.BloggieApplication
import es.chewiegames.bloggie.di.component.ApplicationComponent

abstract class BaseFragment : Fragment() {

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = layoutInflater.inflate(getLayoutId(), container, false)
        bindViews(view)
        initView()
        return view
    }

    /**
     * Use this method to initialize view components. This method is called after [ ][BaseFragment.bindViews] ()}
     */
    open fun initView() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies(BloggieApplication.getComponent()!!, context)
        // can be used for general purpose in all Fragments of Application
    }

    /**
     * Every object annotated with [butterknife.BindViews] its gonna injected trough butterknife
     */
    private fun bindViews(v: View) {
        //ButterKnife.bind(this, v)
    }

    /**
     * This method implements in every activity to inject the dependences
     * @param component ApplicationComponent reference to inject the dependences
     */
    protected abstract fun injectDependencies(component: ApplicationComponent, context: Context)

    /**
     * @return The layout id that's gonna be the fragment view.
     */
    protected abstract fun getLayoutId(): Int
}