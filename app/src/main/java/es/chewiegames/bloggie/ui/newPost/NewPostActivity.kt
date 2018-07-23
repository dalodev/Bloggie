package es.chewiegames.bloggie.ui.newPost

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_new_post.*

class NewPostActivity : BaseActivity(), NewPostView, BottomNavigationView.OnNavigationItemSelectedListener {


    /**
     * Get the layout view of the activity
     * @return The layout id of the activity
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_new_post
    }

    /**
     * This method is triggered in onCreate event
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    /**
     * Initialize the inject dependences for this activity. This method is triggered in onCreate event
     * @param component
     */
    override fun injectDependencies(component: ApplicationComponent) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.home ->{
                supportFinishAfterTransition()
                super.onBackPressed()
            }
            R.id.edit_text_menu ->{

            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.textNavigation ->{

            }

            R.id.imageNavigation ->{

            }

            R.id.publishNavigation ->{

            }
        }

        return true
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}
