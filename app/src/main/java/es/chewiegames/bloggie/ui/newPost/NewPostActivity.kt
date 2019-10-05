package es.chewiegames.bloggie.ui.newPost

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.NewPostModule
import es.chewiegames.bloggie.model.PostContent
import es.chewiegames.bloggie.presenter.newPost.INewPostPresenter
import es.chewiegames.bloggie.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_new_post.*
import javax.inject.Inject
import androidx.recyclerview.widget.DefaultItemAnimator
import kotlinx.android.synthetic.main.content_new_post.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.util.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

class NewPostActivity : BaseActivity(), NewPostView, BottomNavigationView.OnNavigationItemSelectedListener, PostAdapter.PostAdapterListener {

    @Inject
    lateinit var mNewPostPresenter: INewPostPresenter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: PostAdapter

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
        setAdapter()
    }

    /**
     * Initialize the inject dependences for this activity. This method is triggered in onCreate event
     * @param component
     */
    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(NewPostModule(this, this, this)).inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                super.onBackPressed()
            }
            R.id.edit_text_menu -> {
                mNewPostPresenter.onChangePostTitle(this)
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.textNavigation -> {
                mNewPostPresenter.onAddTextContent(this)
                return true
            }

            R.id.imageNavigation -> {
                mNewPostPresenter.onChoosePhotoPicker(BLOG_CONTENT_IMAGE, null)
                return true
            }

            R.id.publishNavigation -> {
                mNewPostPresenter.publishPost(blogImageView)
                return true
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) mNewPostPresenter.onActivityResult(requestCode, resultCode, data, this)
    }

    fun contentClicked(v: View) {
        mNewPostPresenter.onAddContent()
    }

    fun onToolbarImageClicked(v: View) {
        mNewPostPresenter.onChoosePhotoPicker(BLOG_TITLE_IMAGE, null)
    }

    override fun setAdapter() {
        contentList.layoutManager = layoutManager
        contentList.itemAnimator = DefaultItemAnimator()
        contentList.adapter = adapter
        val callback = SimpleItemTouchHelperCallback.Builder(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.END)
                .setAdapter(adapter)
                .bgColorSwipeLeft(ContextCompat.getColor(this, R.color.delete_red))
                .bgColorSwipeRight(ContextCompat.getColor(this, R.color.delete_red))
                .drawableSwipeLeft(ContextCompat.getDrawable(this, R.drawable.ic_delete)!!)
                .drawableSwipeRight(ContextCompat.getDrawable(this, R.drawable.ic_delete)!!)
                .setSwipeEnabled(true)
                .build()
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(contentList)
    }

    /**
     * This method display an image into view with specific size
     * @param imageView the to inflate the image
     * @param imageUri Uri image to display
     * @param size specific size to display image
     */
    private fun displayImage(imageView: ImageView, imageUri: Uri, size: Int) {
        Picasso.with(imageView.context)
                .load(imageUri)
                .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .resize(size, size)
                .centerInside()
                .into(imageView)
    }

    override fun onChangeTitle(title: String) {
        collapsingToolBar.title = title
    }

    /**
     * This method start call to super method startActivityForResult to handle
     * @param intent the lunched intent
     * @param photoPicker the constant arbitrary to handle photopicker
     * @param request the constant arbitrary to handle result
     */
    override fun onStartActivityForResult(intent: Intent, photoPicker: Int, request: Int) {
        startActivityForResult(intent, request)
    }

    /**
     * Display the selected Image on toolbar
     * @param size specific size to display image
     */
    override fun onTitleImageSelected(imageUri: Uri, size: Int) {
        addPhotoButton.visibility = View.GONE
        blogImageView.visibility = View.VISIBLE
        displayImage(blogImageView, imageUri, size)
    }

    override fun navigateToHomeActivity() {
        finish()
    }

    override fun showTitleNameDialog() {
        mNewPostPresenter.onChangePostTitle(this)
    }

    override fun updateAdapterView(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun showInstructions() {
        instructionMessage.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    override fun removeContent(position: Int) {
        adapter.notifyItemRemoved(position)
    }

    override fun addItem() {
        adapter.notifyItemInserted(adapter.itemCount - 1)
    }

    override fun showUndoSnackbar(deletedItem: PostContent, deletedIndex: Int) {
        // showing snack bar with Undo option
        val snackbar = Snackbar.make(coordinatorLayout, resources.getString(R.string.removed_from_content), Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, navigation.height)
        snackbar.view.layoutParams = params
        snackbar.setAction(resources.getString(R.string.undo)) {
            // undo is selected, restore the deleted item
            adapter.restoreItem(deletedItem, deletedIndex)
            showInstructions()
        }
        snackbar.setActionTextColor(Color.YELLOW)
        snackbar.show()
    }

    override fun onAddTextContent(content: PostContent, textContent: String) {
        Utils.hideKeyBoard(this, contentList)
        mNewPostPresenter.setTextContent(content, textContent)
    }

    override fun onEditTextContent(content: PostContent) {
        mNewPostPresenter.editTextContent(content)
    }

    override fun onItemSwiped(deletedItem: PostContent, deletedIndex: Int) {
        // remove the item from recycler view
        adapter.removeItem(deletedIndex)
        mNewPostPresenter.itemSwiped(deletedItem, deletedIndex)
    }

    override fun onImageClicked(content: PostContent) {
        mNewPostPresenter.onChoosePhotoPicker(BLOG_CONTENT_IMAGE, content)
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}
