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
    var appId: String,

    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.APP_USER_NAME)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.APP_USER_NAME)
    @Expose
    var appName: String?,

    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.SEX)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.SEX)
    @Expose
    var Sex: String?,

    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.DATE_OF_BIRTH)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.DATE_OF_BIRTH)
    @Expose
    var DateOfBirth: String?,

    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.HOME_ADDRESS)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.HOME_ADDRESS)
    @Expose
    var homeAddress: String?,

    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.HEIGHT)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.HEIGHT)
    @Expose
    var height: String?,

    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.NEXT_OF_KIN)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.NEXT_OF_KIN)
    @Expose
    var nextOfKin: String?,

    @SerializedName(DatabaseConstants.WorkOutDetailsTable.ColumnNames.PHONE_NUMBER)
    @ColumnInfo(name = DatabaseConstants.WorkOutDetailsTable.ColumnNames.PHONE_NUMBER)
    @Expose
    var phoneNumber: String?,



        )
