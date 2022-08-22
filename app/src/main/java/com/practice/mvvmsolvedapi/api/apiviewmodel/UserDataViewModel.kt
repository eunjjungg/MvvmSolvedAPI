package com.practice.mvvmsolvedapi.api.apiviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.mvvmsolvedapi.api.solvedAcAPIRepository
import com.practice.mvvmsolvedapi.model.SolvedAcGetUserDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException

class UserDataViewModel(private val repository: solvedAcAPIRepository): ViewModel() {
    val getUserDataRepositories = MutableLiveData<SolvedAcGetUserDataModel>()

    fun getUserData(handle: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.getUserData(handle).let {
                    response ->
                    Log.d("api_request_url::", response.raw().request.url.toString())
                    Log.d("get_user_api", response.code().toString() + " " + response.message())

                    if(response.code() == 200) {
                        response.body()?.code = response.code()
                        response.body()?.let { setTierText(it) }
                        getUserDataRepositories.postValue(response.body())
                        Log.d("api_success", response.body().toString())
                    } else {
                        getUserDataRepositories.postValue(
                            SolvedAcGetUserDataModel(
                            "",
                            "",
                            mutableListOf(),
                            SolvedAcGetUserDataModel.BackgroundData("", "", ""),
                            "" ,
                            0,
                            0
                        ).apply { code = response.code() }
                        )
                    }
                }
            } catch (e: ConnectException) {
                e.printStackTrace()
                Log.d("api_exception", e.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("api_exception", e.toString())
            }
        }
    }

    private fun setTierText(model: SolvedAcGetUserDataModel) {
        when {
            model.tier < 6 -> {
                model.tierText = "Bronze " + (6 - model.tier)
            }
            model.tier < 11 -> {
                model.tierText = "Silver " + (11 - model.tier)
            }
            model.tier < 16 -> {
                model.tierText = "Gold " + (16 - model.tier)
            }
            model.tier < 21 -> {
                model.tierText = "Platinum " + (21 - model.tier)
            }
            model.tier < 26 -> {
                model.tierText = "Diamond " + (26 - model.tier)
            }
            model.tier < 31 -> {
                model.tierText = "Ruby " + (31 - model.tier)
            }
            model.tier < 36 -> {
                model.tierText = "Master"
            }
        }
    }
}