package com.example.mybuddygym.database.model.user

import android.app.Application
import javax.inject.Inject


class UserRepoImpl @Inject constructor(
    private val app: Application,
    private val userDao: UserDao
) : UserRepo {

    override fun insert(user: User) {
        return  userDao.insert(user) }


    override fun insert(users: List<User>) {
        return userDao.insert(users)
    }

    override fun clearAllUser() {
        userDao.clearAllSUser()
    }

    override fun getUserByFirstName(): List<String>? {
      return userDao.getUserByFirstName()
    }

    override fun findUser(userId: String): User? {
        return userDao.findUser(userId)
    }

}



