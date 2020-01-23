package es.littledavity.core.di.modules

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import es.littledavity.core.api.repositories.UserRepository
import es.littledavity.core.mapper.UserResponseMapper
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class FirebaseDatabaseUserModule {

    /**
     * Create a provider method for user [DatabaseReference].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @Singleton
    @Provides
    @Named("users")
    fun provideUserDatabase(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        val userDatabase = firebaseDatabase.getReference("users")
        userDatabase.keepSynced(false)
        return userDatabase
    }

    /**
     * Create a provider method for user [UserRepository].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideUserRepository(
        userDatabase: DatabaseReference,
        userResponseMapper: UserResponseMapper
    ) = UserRepository(userDatabase, userResponseMapper)

    /**
     * Create a provider method binding for [UserResponseMapper].
     *
     * @return instance of mapper.
     * @see Provides
     */
    @Provides
    fun providesUserMapper() = UserResponseMapper()
}
