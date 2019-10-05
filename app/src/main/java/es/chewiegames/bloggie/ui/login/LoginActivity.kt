package es.chewiegames.bloggie.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import es.chewiegames.bloggie.R
import com.google.android.material.snackbar.Snackbar
import es.chewiegames.bloggie.ui.main.MainActivity
import androidx.lifecycle.Observer
import es.chewiegames.bloggie.databinding.ActivityLoginBinding
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.ui.base.BaseActivity
import es.chewiegames.bloggie.ui.base.BaseBindingActivity
import es.chewiegames.bloggie.util.RC_SIGN_IN
import es.chewiegames.bloggie.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {


    override fun injectDependencies(component: ApplicationComponent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = this.javaClass.simpleName

    private val viewModel: LoginViewModel by viewModel()
    lateinit var binding: ActivityLoginBinding

    /**
     * Get the layout view of the activity
     *
     * @return The layout id of the activity
     */
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        binding.loginViewModel = viewModel
    }

    override fun initObservers() {
        viewModel.startActivityForResult.observe(this, Observer {
            startActivityForResult(it, RC_SIGN_IN)
        })
        viewModel.goToMainActivity.observe(this, Observer {
            goToActivity(MainActivity(), this)
        })
        viewModel.showMessage.observe(this, Observer {
            Snackbar.make(binding.root, resources.getString(it), Snackbar.LENGTH_SHORT).show()
        })
    }
    override fun onResume() {
        super.onResume()
        viewModel.checkForUserLogin()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null){
            viewModel.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*override fun destroyView() {
    }*/
}
