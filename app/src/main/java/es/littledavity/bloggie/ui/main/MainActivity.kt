/*
 * Copyright 2019 littledavity
 */
package es.littledavity.bloggie.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.littledavity.bloggie.R
import es.littledavity.bloggie.databinding.ActivityMainBinding
import es.littledavity.bloggie.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int = R.layout.activity_main

    /**
     * This method is triggered in onCreate event
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val host = my_nav_host_fragment as NavHostFragment? ?: return
        val navController = host.navController
        binding.navigation.setupWithNavController(navController)
        binding.navigation.setOnNavigationItemSelectedListener(this)
    }

    /**
     * this method trigger when user click on any of items in bottom menu
     * @param item single component of menu
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.newPostActivity -> {
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.action_to_new_post)
            }
        }
        return true
    }
}
