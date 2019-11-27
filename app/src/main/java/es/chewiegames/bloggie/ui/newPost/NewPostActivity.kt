package es.chewiegames.bloggie.ui.newPost

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.databinding.ActivityNewPostBinding
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.ui.base.BaseActivity
import es.chewiegames.bloggie.util.BLOG_CONTENT_IMAGE
import es.chewiegames.bloggie.viewmodel.NewPostViewModel
import es.chewiegames.domain.model.PostContent
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPostActivity : BaseActivity<ActivityNewPostBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val viewModel: NewPostViewModel by viewModel()
    private val adapter: PostAdapter by lazy { PostAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(getLayoutId())
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupToolbar()
        initView(savedInstanceState)
        initObservers()
    }

    /**
     * Get the layout view of the activity
     * @return The layout id of the activity
     */
    override fun getLayoutId() = R.layout.activity_new_post

    /**
     * This method is triggered in onCreate event
     */
    override fun initView(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        binding.navigation.setOnNavigationItemSelectedListener(this)
        setAdapter()
    }

    override fun initObservers() {
        viewModel.navigateToHome.observe(this, Observer { finish() })
        viewModel.showUndoPostContent.observe(this, Observer { showUndoSnackbar(it) })
        viewModel.postContentImageType.observe(this, Observer { onChoosePhotoPicker(it) })
        viewModel.showTitleDialog.observe(this, Observer { changePostTitle() })
        viewModel.addAdapterItem.observe(this, Observer { adapter.addItem(it) })
        viewModel.updateAdapterPosition.observe(this, Observer { adapter.updateAdapterView(it) })
        viewModel.removeAdapterItem.observe(this, Observer { adapter.removeContent(it) })
    }

    override fun onSupportNavigateUp() = findNavController(R.id.my_nav_host_fragment).navigateUp()

    /**
     * Initialize the inject dependences for this activity. This method is triggered in onCreate event
     * @param component
     */
    override fun injectDependencies(component: ApplicationComponent) {}

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
            R.id.edit_text_menu -> changePostTitle()
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.textNavigation -> {
                viewModel.addTextContent()
                return true
            }
            R.id.imageNavigation -> {
                onChoosePhotoPicker(BLOG_CONTENT_IMAGE)
                return true
            }
            R.id.publishNavigation -> {
                viewModel.publishPost(binding.blogImageView)
                return true
            }
        }
        return false
    }

    private fun onChoosePhotoPicker(imageViewRequest: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        onStartActivityForResult(photoPickerIntent, imageViewRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) viewModel.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setAdapter() {
        binding.contentNewPostLayout.contentList.layoutManager = LinearLayoutManager(this)
        binding.contentNewPostLayout.contentList.itemAnimator = DefaultItemAnimator()
        binding.contentNewPostLayout.contentList.adapter = adapter
        val callback = SimpleItemTouchHelperCallback.Builder(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.END)
                .setAdapter(adapter)
                .bgColorSwipeLeft(ContextCompat.getColor(this, R.color.delete_red))
                .bgColorSwipeRight(ContextCompat.getColor(this, R.color.delete_red))
                .drawableSwipeLeft(ContextCompat.getDrawable(this, R.drawable.ic_delete)!!)
                .drawableSwipeRight(ContextCompat.getDrawable(this, R.drawable.ic_delete)!!)
                .setSwipeEnabled(true)
                .build()
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.contentNewPostLayout.contentList)
    }

    /**
     * This method start call to super method startActivityForResult to handle
     * @param intent the lunched intent
     * @param request the constant arbitrary to handle result
     */
    private fun onStartActivityForResult(intent: Intent, request: Int) = startActivityForResult(intent, request)

    private fun changePostTitle() {
        val dialogBuilder = AlertDialog.Builder(this)
        val viewContent: View = layoutInflater.inflate(R.layout.title_edit_text_dialog, null)
        dialogBuilder.setView(viewContent)
        val editText: EditText = viewContent.findViewById(R.id.post_title_edittext)
        dialogBuilder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            binding.collapsingToolBar.title = editText.text.toString()
            viewModel.post.value?.title = editText.text.toString()
            dialog.dismiss()
        }

        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.collapsingToolBar.title = editText.text.toString()
                viewModel.post.value?.title = editText.text.toString()
                dialog.dismiss()
            }
            false
        }
    }

    private fun showUndoSnackbar(deletedItem: PostContent) {
        // showing snack bar with Undo option
        val snackbar = Snackbar.make(binding.root, resources.getString(R.string.removed_from_content), Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, binding.navigation.height)
        snackbar.view.layoutParams = params
        snackbar.setAction(resources.getString(R.string.undo)) {
            // undo is selected, restore the deleted item
            adapter.restoreItem(deletedItem)
        }
        snackbar.setActionTextColor(Color.YELLOW)
        snackbar.show()
    }
}
