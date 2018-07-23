package es.chewiegames.bloggie.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
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
        //NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
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

   /* override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawerLayout, Navigation.findNavController(this, R.id.my_nav_host_fragment))
    }*/
    override fun onSupportNavigateUp() = findNavController(R.id.my_nav_host_fragment).navigateUp()

    /**
     * this method trigger when user click on any of items in bottom menu
     * @param item single component of menu
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.newPostActivity -> {
                val options = NavOptions.Builder()
                        .setEnterAnim(R.anim.fui_slide_in_right)
                        .setExitAnim(R.anim.fui_slide_out_left)
                        .setPopEnterAnim(R.anim.fui_slide_in_right)
                        .setPopExitAnim(R.anim.fui_slide_out_left)
                        .build()
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.newPostActivity, null, options)
            }
        }
        return true
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}
