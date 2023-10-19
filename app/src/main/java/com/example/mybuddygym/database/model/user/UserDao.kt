package com.example.mybuddygym.database.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mybuddygym.database.DatabaseConstants


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

    @Query("DELETE FROM ${DatabaseConstants.UserTable.TABLE_NAME} ")
    fun clearAllSUser()

    @Query("SELECT * FROM ${DatabaseConstants.UserTable.TABLE_NAME}" +
            " WHERE ${DatabaseConstants.UserTable.ColumnNames.FIRST_NAME}")
    fun getUserByFirstName(): List<String>?
}