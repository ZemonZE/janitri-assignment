package com.pregnancy.vitals.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pregnancy.vitals.data.repository.VitalsRepository

class VitalsViewModelFactory (private val repo: VitalsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VitalsViewModel::class.java)){
           @Suppress("UNCHECKED_CAST")
            return VitalsViewModel(repo) as T
        }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}