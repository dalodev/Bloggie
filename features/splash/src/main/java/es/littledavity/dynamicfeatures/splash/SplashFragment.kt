/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import es.littledavity.bloggie.BloggieApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.splash.databinding.FragmentSplashBinding
import es.littledavity.dynamicfeatures.splash.di.DaggerSplashComponent
import es.littledavity.dynamicfeatures.splash.di.SplashModule
import timber.log.Timber

/**
 * Splash check if login and navigate to login screen or home screen
 *
 * @see BaseFragment
 */
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    layoutId = R.layout.fragment_splash
) {

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        viewModel.checkUserLogin()
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerSplashComponent
            .builder()
            .coreComponent(BloggieApp.coreComponent(requireContext()))
            .splashModule(SplashModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    /**
     * Remove observers
     */
    override fun onClear() {
        viewModel.state.removeObservers(this)
    }

    /**
     * Observer view state change on [SplashViewState].
     *
     * @param viewState State of splash fragment.
     */
    private fun onViewStateChange(viewState: SplashViewState) {
        when (viewState) {
            is SplashViewState.Loading ->
                Timber.i("Splash Loading")
            is SplashViewState.Logged -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(direction)
            }
            is SplashViewState.NotLogged -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                findNavController().navigate(direction)
            }
            is SplashViewState.Error ->
                Timber.i("Error while check user login")
        }
    }
}
