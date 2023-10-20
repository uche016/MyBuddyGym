package com.example.mybuddygym.loginPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.mybuddygym.database.DatabaseMyBuddyApp
import com.example.mybuddygym.database.model.user.User
import com.example.mybuddygym.database.model.user.UserRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel (
    val app: Application,
) : AndroidViewModel(app) {

    private val workManager = WorkManager.getInstance(app)
    private val usersRepo =
        UserRepoImpl(app, DatabaseMyBuddyApp.getInstance(app).userDao())
    private val _loginMapLive = MutableLiveData<Map<String, String?>>()
    internal val loginMapLive: LiveData<Map<String, String?>> get() = _loginMapLive



    fun getUserFromDB(userId: String): User? {
        val bgStaff = runBlocking(Dispatchers.IO) {
            usersRepo.findUser(userId)
        }
        return bgStaff
    }

    fun getUsersFromDB(): List<String>? {
        val user = runBlocking(Dispatchers.IO) {
            usersRepo.getUserByFirstName()
        }
        return user
    }

    fun clearDB() {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepo.clearAllUser()
        }
    }

    fun updateLoginMapLive(loginMap: Map<String, String?>) {
        _loginMapLive.value = loginMap
    }
}
