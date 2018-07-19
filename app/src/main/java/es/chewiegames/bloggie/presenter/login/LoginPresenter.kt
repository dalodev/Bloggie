package es.chewiegames.bloggie.presenter.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Resources
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.ui.login.LoginView
import es.chewiegames.bloggie.interactor.login.ILoginInteractor
import es.chewiegames.bloggie.interactor.login.LoginInteractor
import es.chewiegames.bloggie.util.RC_SIGN_IN
import java.util.*
import javax.inject.Inject

class LoginPresenter @Inject constructor(): ILoginPresenter, ILoginInteractor.OnLaunchResult, ILoginInteractor.OnLoginFinishedListener {

    private var view : LoginView? = null

    @Inject
    lateinit var mLoginInteractor: LoginInteractor

    @Inject
    lateinit var resources: Resources

    override fun setView(view: LoginView) {
        this.view = view
    }

    override fun checkFirebaseAuth() {
        // Choose authentication providers
        val providers = Arrays.asList(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())
        val authIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(true)
                .setLogo(R.mipmap.ic_launcher)      // Set logo drawable
                .setTheme(R.style.SplashTheme)      // Set theme
                .build()
        view!!.onStartActivityForResult(authIntent, RC_SIGN_IN)
    }

    override fun checkForUserLogin() {
        mLoginInteractor.handleLaunch(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if(requestCode == RC_SIGN_IN){
            var response: IdpResponse? = IdpResponse.fromResultIntent(data)
            if(resultCode == RESULT_OK){
                var user : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                mLoginInteractor.storeUserInDatabase(user, this)
            }else{
                if(response == null){
                    view!!.showMessage(resources.getString(R.string.sign_in_canceled))
                    return
                }

               when(response.error!!.errorCode){
                   ErrorCodes.NO_NETWORK -> {
                        view!!.showMessage(resources.getString(R.string.no_internet_connection))
                   }
                   ErrorCodes.UNKNOWN_ERROR -> {
                       view!!.showMessage(resources.getString(R.string.unknown_error))
                   }
               }
                view!!.showLoginButton()
            }
        }else{
            view!!.showLoginButton()
        }
    }

    override fun userLogged() {
        onSuccess()
    }

    override fun userNotLogged() {
        view!!.showLoginButton()
    }

    override fun onError(message: String) {
    }

    override fun onSuccess() {
        view!!.navigateToMainActivity()
    }

    override fun showProgressDialog() {
        view!!.showLoading(true);
    }

    override fun hideProgressDialog() {
        view!!.showLoading(false);
    }
}