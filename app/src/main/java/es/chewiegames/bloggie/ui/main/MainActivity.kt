package es.chewiegames.bloggie.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.MainActivityModule
import es.chewiegames.bloggie.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * Get the layout view of the activity
     * @return The layout id of the activity
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    /**
     * This method is triggered in onCreate event
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val host = my_nav_host_fragment as NavHostFragment? ?: return
        val navController = host.navController
        setupActionBarWithNavController(navController)
        navigation.setupWithNavController(navController)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    /**
     * Initialize the inject dependences for this activity. This method is triggered in onCreate event
     * @param component
     */
    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(MainActivityModule(this, this)).inject(this)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.my_nav_host_fragment).navigateUp()

    /**
     * this method trigger when user click on any of items in bottom menu
     * @param item single component of menu
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.newPostActivity -> {
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.action_to_new_post)
            }
        }
        return true
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}
