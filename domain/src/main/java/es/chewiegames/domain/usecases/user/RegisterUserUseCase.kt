package es.chewiegames.domain.usecases.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseUser
import es.chewiegames.data.repository.UserRepository
import es.chewiegames.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow

class RegisterUserUseCase(private val repository: UserRepository)  {

}