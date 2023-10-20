package com.example.mybuddygym.database.model.user



interface UserRepo {
    fun insert(user: User)

    fun insert(users: List<User>)

    fun clearAllUser()

    fun getUserByFirstName():List<String>?

    fun findUser(userId: String): User?
}