package es.chewiegames.data.koin

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.chewiegames.data.model.User
import es.chewiegames.data.repository.LoginRespository
import es.chewiegames.data.repositoryImpl.LoginRepositoryImpl
import org.koin.dsl.module.module

val dataModule = module {

    single<LoginRespository>{LoginRepositoryImpl(get(),get())}

    factory { provideUser() }
    factory { getUserDatabaseReference() }
}

fun getUserDatabaseReference() : DatabaseReference{
    val databaseReference = FirebaseDatabase.getInstance().getReference("users")
    databaseReference.keepSynced(true)//keep data synced
    return databaseReference
}

fun provideUser(): User {
    return User()
}