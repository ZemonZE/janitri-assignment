package com.pregnancy.vitals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pregnancy.vitals.data.local.dao.VitalsDao
import com.pregnancy.vitals.data.local.entity.VitalsEntity

@Database(entities = [VitalsEntity::class], version = 1, exportSchema = false)
abstract class VitalsDatabase : RoomDatabase() {
    abstract fun vitalsDao(): VitalsDao
    // i will not use companion object as usual , i will use lazy instead because i don't want it complex
}
