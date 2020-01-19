/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import es.littledavity.bloggie.BloggieApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.core.utils.RC_SIGN_IN
import es.littledavity.dynamicfeatures.login.databinding.FragmentLoginBinding
import es.littledavity.dynamicfeatures.login.di.DaggerLoginComponent
import es.littledavity.dynamicfeatures.login.di.LoginModule
import timber.log.Timber

/**
 * Login launch firebase auth ui for login
 *
 * @see BaseFragment
 */
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutId = R.layout.fragment_login
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
        observe(viewModel.authIntent) { startActivityForResult(it, RC_SIGN_IN) }
        observe(viewModel.state, ::onViewStateChange)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerLoginComponent
            .builder()
            .coreComponent(BloggieApp.coreComponent(requireContext()))
            .loginModule(LoginModule(this))
            .build()
            .inject(this)
    }

    /**
     * Remove observers
     */
    override fun onClear() {
        viewModel.state.removeObservers(this)
        viewModel.authIntent.removeObservers(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            viewModel.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Observer view state change on [LoginViewState].
     *
     * @param viewState State of splash fragment.
     */
    private fun onViewStateChange(viewState: LoginViewState) {
        when (viewState) {
            is LoginViewState.Loading ->
                Timber.i("Login Loading")
            is LoginViewState.SuccessLogin -> {
                val direction = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(direction)
            }
            is LoginViewState.Error ->
                Timber.i("Login Error")
        }
    }
}
