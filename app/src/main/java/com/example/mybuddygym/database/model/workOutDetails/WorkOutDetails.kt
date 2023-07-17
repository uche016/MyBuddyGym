package com.example.mybuddygym.database.model.workOutDetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mybuddygym.database.DatabaseConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




@Entity(tableName = DatabaseConstants.WorkOutDetailsTable.TABLE_NAME)
data class WorkOutDetails (
    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.APP_USER_ID)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.APP_USER_ID )
    @PrimaryKey
    @Expose
    var id: String,
        )
