/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di

import android.content.Context
import com.google.firebase.database.DatabaseReference
import dagger.Component
import es.littledavity.core.di.modules.ContextModule
import es.littledavity.core.di.modules.FirebaseDatabaseModule
import es.littledavity.core.di.modules.FirebaseDatabasePostModule
import es.littledavity.core.di.modules.FirebaseDatabaseUserModule
import es.littledavity.core.di.modules.UtilsModule
import es.littledavity.core.mapper.UserResponseMapper
import es.littledavity.core.utils.ThemeUtils
import javax.inject.Named
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(modules = [
    ContextModule::class,
    UtilsModule::class,
    FirebaseDatabaseModule::class,
    FirebaseDatabaseUserModule::class,
    FirebaseDatabasePostModule::class
])
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun context(): Context

    /**
     * Provide dependency graph User response mapper
     *
     * @return Context
     */
    fun userResponseMapper(): UserResponseMapper

    /**
     * Provide dependency graph ThemeUtils
     *
     * @return ThemeUtils
     */
    fun themeUtils(): ThemeUtils

    /**
     * Provide dependency graph user database reference
     *
     * @return FirebaseReference
     */
    @Named("users")
    fun userDatabase(): DatabaseReference

    /**
     * Provide dependency graph posts database reference
     *
     * @return FirebaseReference
     */
    @Named("posts")
    fun postDatabase(): DatabaseReference

    /**
     * Provide dependency graph posts database reference
     *
     * @return FirebaseReference
     */
    @Named("postsByUser")
    fun postsByUserDatabase(): DatabaseReference

    /**
     * Provide dependency graph posts database reference
     *
     * @return FirebaseReference
     */
    @Named("likedPostsByUser")
    fun likedPostsByUserDatabase(): DatabaseReference
}
