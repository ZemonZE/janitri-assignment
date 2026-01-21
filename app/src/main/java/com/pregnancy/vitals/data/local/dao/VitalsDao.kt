package com.pregnancy.vitals.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pregnancy.vitals.data.local.entity.VitalsEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface VitalsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vitals: VitalsEntity)

    @Query("SELECT * FROM vitals ORDER BY timestamp DESC")
    fun getAll(): Flow<List<VitalsEntity>>
}