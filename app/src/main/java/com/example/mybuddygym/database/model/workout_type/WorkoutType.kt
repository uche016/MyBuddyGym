package com.example.mybuddygym.database.model.workout_type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mybuddygym.database.DatabaseConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = DatabaseConstants.WorkOutDetailsTable.TABLE_NAME)
data class WorkOutType (
    @SerializedName(DatabaseConstants.WorkoutTypeTable.ColumnNames.WORKOUT_NAME)
    @ColumnInfo(name = DatabaseConstants.WorkoutTypeTable.ColumnNames.WORKOUT_NAME)
    @PrimaryKey
    @Expose
    var workoutName: String,

    @SerializedName(DatabaseConstants.WorkoutTypeTable.ColumnNames.WORKOUT_IMAGE)
    @ColumnInfo(name = DatabaseConstants.WorkoutTypeTable.ColumnNames.WORKOUT_IMAGE)
    @Expose
    var workoutImage: String?,





    )
