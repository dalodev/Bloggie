package es.littledavity.bloggie.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import es.littledavity.bloggie.R
import es.littledavity.bloggie.databinding.ActivityLoginBinding
import es.littledavity.bloggie.ui.base.BaseActivity
import es.littledavity.bloggie.ui.main.MainActivity
import es.littledavity.bloggie.util.RC_SIGN_IN
import es.littledavity.bloggie.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.loginViewModel = viewModel
    }

    override fun initObservers() {
        viewModel.startActivityForResult.observe(this, Observer { startActivityForResult(it, RC_SIGN_IN) })
        viewModel.goToMainActivity.observe(this, Observer { goToActivity(MainActivity(), this) })
        viewModel.message.observe(this, Observer { Snackbar.make(binding.root, resources.getString(it), Snackbar.LENGTH_SHORT).show() })
        viewModel.error.observe(this, Observer { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            viewModel.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*override fun destroyView() {
    }*/
}
