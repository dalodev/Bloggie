/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import es.littledavity.bloggie.BloggieApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.bindings.postTitleImage
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.core.utils.BLOG_CONTENT_IMAGE
import es.littledavity.core.utils.BLOG_TITLE_IMAGE
import es.littledavity.core.utils.EXTRA_POST_CONTENT
import es.littledavity.core.utils.ImagePicker
import es.littledavity.core.utils.MAX_HEIGHT
import es.littledavity.core.utils.MAX_WIDTH
import es.littledavity.core.utils.PERMISSION_CODE
import es.littledavity.domain.model.PostContent
import es.littledavity.dynamicfeatures.newpost.adapter.PostAdapter
import es.littledavity.dynamicfeatures.newpost.adapter.SimpleItemTouchHelperCallback
import es.littledavity.dynamicfeatures.newpost.databinding.FragmentNewPostBinding
import es.littledavity.dynamicfeatures.newpost.di.DaggerNewPostComponent
import es.littledavity.dynamicfeatures.newpost.di.NewPostModule
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

/**
 * View listing the all feed posts, ours and follow with option to display the detail view.
 *
 * @see BaseFragment
 */
class NewPostFragment : BaseFragment<FragmentNewPostBinding, NewPostViewModel>(
    layoutId = R.layout.fragment_new_post
), BottomNavigationView.OnNavigationItemSelectedListener {

    private val adapter: PostAdapter by lazy { PostAdapter(viewModel) }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.event, ::onViewEvent)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerNewPostComponent
            .builder()
            .coreComponent(BloggieApp.coreComponent(requireContext()))
            .newPostModule(NewPostModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        setAdapter()
    }

    /**
     * Clear all listeners and observers of [NewPostFragment]
     */
    override fun onClear() {}

    /**
     * Initialize the contents of the Fragment host's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @param inflater Inflater to instantiate menu XML files into Menu objects.
     * @see BaseFragment.onCreateOptionsMenu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_post, menu)
    }

    /**
     * This methor is trigger when click on menu item
     *
     * @param item Menu item who is in menu options
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                goBack()
            R.id.edit_text_menu ->
                ChangePostNameDialog.builder(context, layoutInflater, viewBinding, viewModel)
        }
        return true
    }

    /**
     * handle requested permission result
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    // permission from popup granted
                    pickImageFromGallery(BLOG_CONTENT_IMAGE)
                } else {
                    // permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                context?.let {
                    val bitmap = ImagePicker.getImageFromResult(it, data)
                    when (requestCode) {
                        BLOG_TITLE_IMAGE -> viewBinding.blogImageView.postTitleImage(
                            uri,
                            ceil(sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
                        )
                        BLOG_CONTENT_IMAGE ->
                            viewModel.addImageContentItem(uri, bitmap, data.getSerializableExtra(EXTRA_POST_CONTENT) as PostContent)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Trigger when user click in bottom menu items
     */
    @ExperimentalCoroutinesApi
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.textNavigation -> viewModel.addEditTextContentItem()
            R.id.imageNavigation -> viewModel.onAddImageClicked(activity!!, BLOG_CONTENT_IMAGE)
            R.id.publishNavigation -> viewModel.publish(viewBinding.blogImageView)
            else -> false
        }
    }

    // ============================================================================================
    //  Private setups methods
    // ============================================================================================

    /**
     * Observer view state change on [NewPostViewModel].
     *
     * @param viewState State of Posting.
     */
    private fun onViewStateChange(viewState: NewPostViewState) {
        when (viewState) {
            is NewPostViewState.PickImageFromGalleryTitle ->
                pickImageFromGallery(BLOG_TITLE_IMAGE)
            is NewPostViewState.PickImageFromGalleryContent ->
                pickImageFromGallery(BLOG_CONTENT_IMAGE, viewState.content)
            is NewPostViewState.ItemDeleted ->
                showUndoSnackbar(viewState.postContent)
        }
    }

    /**
     * Observer view event change on [NewPostViewModel].
     *
     * @param viewEvent Event on characters list.
     */
    private fun onViewEvent(viewEvent: NewPostViewEvent) {
        when (viewEvent) {
            is NewPostViewEvent.OpenFeed -> {
                Timber.i("Open post detail: $viewEvent.id")
                goBack()
            }
        }
    }
    /**
     * Observer view event change on [NewPostViewModel].
     *
     * @param viewEvent Event on characters list.
     */
    private fun onViewDataChange() {
    }

    /**
     * This method start call to super method startActivityForResult to handle photo picker
     * @param imageViewRequest title or content image view request code
     */
    private fun pickImageFromGallery(imageViewRequest: Int, content: PostContent? = null) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.putExtra(EXTRA_POST_CONTENT, content)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, imageViewRequest)
    }

    private fun setAdapter() {
        viewBinding.contentList.layoutManager = LinearLayoutManager(context)
        viewBinding.contentList.itemAnimator = DefaultItemAnimator()
        viewBinding.contentList.adapter = adapter
        context?.let {
            val callback = SimpleItemTouchHelperCallback.Builder(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.END)
                .setAdapter(adapter)
                .bgColorSwipeLeft(ContextCompat.getColor(it, android.R.color.transparent))
                .bgColorSwipeRight(ContextCompat.getColor(it, android.R.color.transparent))
                .drawableSwipeLeft(ContextCompat.getDrawable(it, R.drawable.ic_delete)!!)
                .drawableSwipeRight(ContextCompat.getDrawable(it, R.drawable.ic_delete)!!)
                .setSwipeEnabled(true)
                .setCardView(view?.findViewById(R.id.foregroundView) as? CardView)
                .build()
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(viewBinding.contentList)
        }
    }

    private fun showUndoSnackbar(deletedItem: PostContent) {
        // showing snack bar with Undo option
        val snackBar = Snackbar.make(viewBinding.root, resources.getString(R.string.removed_from_content), Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, viewBinding.bottomNavigation.height)
        snackBar.view.layoutParams = params
        snackBar.setAction(resources.getString(R.string.undo)) {
            // undo is selected, restore the deleted item
            adapter.restoreItem(deletedItem)
        }
        snackBar.setActionTextColor(Color.YELLOW)
        snackBar.show()
    }

    private fun goBack() = findNavController().popBackStack()
}
