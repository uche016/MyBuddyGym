package com.example.mybuddygym.database.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mybuddygym.database.DatabaseConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




@Entity(tableName = DatabaseConstants.UserTable.TABLE_NAME)
data class User (


    @SerializedName(DatabaseConstants.UserTable.ColumnNames.USER_ID)
    @ColumnInfo(name = DatabaseConstants.UserTable.ColumnNames.USER_ID)
    @PrimaryKey
    @Expose
    var id: String,

    @SerializedName(DatabaseConstants.UserTable.ColumnNames.FIRST_NAME)
    @ColumnInfo(name = DatabaseConstants.UserTable.ColumnNames.FIRST_NAME)
    @Expose
    var firstName: String?,


    @SerializedName(DatabaseConstants.UserTable.ColumnNames.LAST_NAME)
    @ColumnInfo(name = DatabaseConstants.UserTable.ColumnNames.LAST_NAME)
    @Expose
    var lastName: String?,

    @SerializedName(DatabaseConstants.UserTable.ColumnNames.PASSWORD)
    @ColumnInfo(name = DatabaseConstants.UserTable.ColumnNames.PASSWORD)
    @Expose
    var password: String?,

    @SerializedName(DatabaseConstants.UserTable.ColumnNames.HUB)
    @ColumnInfo(name = DatabaseConstants.UserTable.ColumnNames.HUB)
    @Expose
    var hub: String?,

    @SerializedName(DatabaseConstants.UserTable.ColumnNames.TEMPLATE)
    @ColumnInfo(name =DatabaseConstants.UserTable.ColumnNames.TEMPLATE)
    @Expose
    var template: String?,

    @SerializedName(DatabaseConstants.UserTable.ColumnNames.ACCESS_LEVEL)
    @ColumnInfo(name = DatabaseConstants.UserTable.ColumnNames.ACCESS_LEVEL)
    @Expose
    var accessLevel: String?,

    )