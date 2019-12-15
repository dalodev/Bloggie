package es.littledavity.bloggie.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    lateinit var binding: B

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindView(inflater, container, getLayoutId())
        initObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    /**
     * Binding the view with layout resource
     */
    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, layoutId: Int) {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
    }
    /**
     * Start lifecycle  methods
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * init viewmodel observers
     */
    open fun initObservers() {}
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

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    open fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction {
            replace(frameId, fragment, fragment.javaClass.simpleName)
            addToBackStack(fragment.javaClass.simpleName)
        }
    }

    /**
     * Activity navigation.
     */
    fun goToActivity(intent: Intent, bundle: Bundle) {
        startActivity(intent, bundle)
    }
}
