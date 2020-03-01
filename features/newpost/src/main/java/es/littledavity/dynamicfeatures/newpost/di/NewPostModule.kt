/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.newpost.di

import androidx.annotation.VisibleForTesting
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.api.repositories.NewPostRepository
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.domain.model.CommentMapper
import es.littledavity.domain.model.NewPostMapper
import es.littledavity.domain.model.PostContentMapper
import es.littledavity.domain.model.UserMapper
import es.littledavity.dynamicfeatures.newpost.NewPostFragment
import es.littledavity.dynamicfeatures.newpost.NewPostViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Class that contributes to the object graph [NewPostComponent].
 *
 * @see Module
 */
@Module
class NewPostModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment: NewPostFragment
) {
    /**
     * Create a provider method binding for [NewPostViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @ExperimentalCoroutinesApi
    @Provides
    @FeatureScope
    fun providesNewPostViewModel(
        repository: NewPostRepository,
        newPostMapper: NewPostMapper
    ) = fragment.viewModel {
        NewPostViewModel(
            repository = repository,
            newPostMapper = newPostMapper
        )
    }

    /**
     * Create a provider method binding for [NewPostMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesNewPostMapper(
        userMapper: UserMapper,
        postContentMapper: PostContentMapper,
        commentMapper: CommentMapper
    ) = NewPostMapper(userMapper, postContentMapper, commentMapper)

    /**
     * Create a provider method binding for [UserMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesUserMapper() = UserMapper()

    /**
     * Create a provider method binding for [CommentMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesCommentMapper(
        userMapper: UserMapper
    ) = CommentMapper(userMapper)

    /**
     * Create a provider method binding for [PostContentMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesPostContentMapper() = PostContentMapper()
}
