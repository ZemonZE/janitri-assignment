package com.pregnancy.vitals.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sysBp:Int,
    val diaBp:Int,
    val heartRate:Int,
    val weightKg: Double,
    val babyKicks:Int,
    val timestamp:Long = System.currentTimeMillis()
)