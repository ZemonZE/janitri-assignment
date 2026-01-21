package com.pregnancy.vitals.data.repository

import com.pregnancy.vitals.data.local.entity.VitalsEntity
import kotlinx.coroutines.flow.Flow

interface VitalsRepository{
    suspend fun insert(vitals: VitalsEntity)
    fun getAll(): Flow<List<VitalsEntity>>
}
