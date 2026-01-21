package com.pregnancy.vitals.data.repository

import com.pregnancy.vitals.data.local.dao.VitalsDao
import com.pregnancy.vitals.data.local.entity.VitalsEntity
import kotlinx.coroutines.flow.Flow

class VitalsRepositoryImpl(private val vitalsDao: VitalsDao):VitalsRepository {
    override suspend fun insert(vitals: VitalsEntity) {
        vitalsDao.insert(vitals)
    }
    override fun getAll(): Flow<List<VitalsEntity>> = vitalsDao.getAll()

}