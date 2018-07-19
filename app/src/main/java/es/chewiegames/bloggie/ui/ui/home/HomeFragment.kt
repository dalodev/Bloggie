package es.chewiegames.bloggie.ui.ui.home

import android.content.Context
import android.view.View
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.HomeModule
import es.chewiegames.bloggie.presenter.home.IHomePresenter
import es.chewiegames.bloggie.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView {

     @Inject
     lateinit var mHomePresenter: IHomePresenter


    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun injectDependencies(component: ApplicationComponent, context: Context) {
        component.plus(HomeModule(this)).inject(this)
    }

    override fun initView() {
        super.initView()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}