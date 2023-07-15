package com.example.mybuddygym.database.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

    @Query("DELETE FROM User")
    fun clearAllStaff()

}