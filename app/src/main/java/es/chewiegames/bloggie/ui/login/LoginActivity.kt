package es.chewiegames.bloggie.ui.login

import android.content.Intent
import android.os.Bundle
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.LoginModule
import es.chewiegames.bloggie.presenter.login.ILoginPresenter
import es.chewiegames.bloggie.ui.BaseActivity
import javax.inject.Inject
import android.support.design.widget.Snackbar
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginView {

    private val TAG = this.javaClass.simpleName

    /**
     * Dependences reference
     */
    @Inject
    lateinit var mLoginPresenter: ILoginPresenter

    /**
     * Get the layout view of the activity
     *
     * @return The layout id of the activity
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    /**
     * Initialize the inject dependences for this activity. This method is triggered in onCreate event
     *
     * @param component
     */
    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(LoginModule(this, this)).inject(this)
    }

    /**
     * This method is triggered in onCreate event
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initializePresenter()
    }

    /**
     * call presenter to check if user logged in
     */
    private fun initializePresenter() {
        mLoginPresenter.checkForUserLogin()
    }

    /**
     * This method is triggered when user click on get started button
     */
    fun onGetStartedClick(view: View){
        mLoginPresenter.checkFirebaseAuth()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mLoginPresenter.onActivityResult(requestCode, resultCode, data!!)
    }

    override fun onStartActivityForResult(intent: Intent, request: Int) {
        startActivityForResult(intent, request)
    }

    override fun navigateToMainActivity() {
    }

    override fun showLoginButton() {
        getStartedButton.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        Snackbar.make(getStartedButton, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoading(show: Boolean) {
        showProgressBar()
    }

    /**
     * this method show/hide a progress dialog
     * @param show boolean parameter to show/hide view
     */
    fun showProgressBar(){
        getStartedButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

}
