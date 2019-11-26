package es.chewiegames.bloggie.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import es.chewiegames.bloggie.R
import com.google.android.material.snackbar.Snackbar
import es.chewiegames.bloggie.ui.main.MainActivity
import androidx.lifecycle.Observer
import es.chewiegames.bloggie.databinding.ActivityLoginBinding
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.ui.base.BaseActivity
import es.chewiegames.bloggie.util.RC_SIGN_IN
import es.chewiegames.bloggie.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun injectDependencies(component: ApplicationComponent) {}

    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        binding.loginViewModel = viewModel
    }

    override fun initObservers() {
        viewModel.startActivityForResult.observe(this, Observer { startActivityForResult(it, RC_SIGN_IN) })
        viewModel.goToMainActivity.observe(this, Observer { goToActivity(MainActivity(), this) })
        viewModel.message.observe(this, Observer { Snackbar.make(binding.root, resources.getString(it), Snackbar.LENGTH_SHORT).show() })
        viewModel.error.observe(this, Observer { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() })
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