package com.pregnancy.vitals.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pregnancy.vitals.data.local.entity.VitalsEntity
import com.pregnancy.vitals.data.repository.VitalsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VitalsViewModel(private val repo: VitalsRepository) : ViewModel(){
    val vitals : StateFlow<List<VitalsEntity>> = repo.getAll()
        // here why i implement SharingStarted.WhileSubscribed(), the following scenario :
        // screen is up and state is collected , there is a flow
        // user go to another app , wait 5 second and stop collecting and within 5 seconds i wont susbsribe again
        // because last data is still collected
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    fun addVitals(sys:Int , dia:Int , heart:Int , weight:Double , kicks:Int){
        viewModelScope.launch {
            repo.insert(
                VitalsEntity(
                    sysBp = sys,
                    diaBp = dia,
                    heartRate = heart,
                    weightKg = weight,
                    babyKicks = kicks
                )
            )
        }
    }
}