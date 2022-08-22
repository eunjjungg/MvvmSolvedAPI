package com.practice.mvvmsolvedapi.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class APIViewModelFactory(private val repository: solvedAcAPIRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(solvedAcAPIRepository::class.java).newInstance(repository)
    }
}