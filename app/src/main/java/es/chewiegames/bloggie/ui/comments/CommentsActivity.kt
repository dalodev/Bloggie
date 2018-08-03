package es.chewiegames.bloggie.ui.comments

import android.os.Bundle
import android.view.MenuItem
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.ui.BaseActivity

class CommentsActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_comments
    }

    override fun injectDependencies(component: ApplicationComponent) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            android.R.id.home ->{
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
