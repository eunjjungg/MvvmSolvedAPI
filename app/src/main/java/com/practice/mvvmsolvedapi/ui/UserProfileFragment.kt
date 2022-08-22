package com.practice.mvvmsolvedapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.practice.mvvmsolvedapi.R
import com.practice.mvvmsolvedapi.api.APIViewModelFactory
import com.practice.mvvmsolvedapi.api.apiviewmodel.UserDataViewModel
import com.practice.mvvmsolvedapi.api.solvedAcAPIRepository
import com.practice.mvvmsolvedapi.databinding.FragmentUserProfileBinding
import retrofit2.converter.gson.GsonConverterFactory


class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>() {
    override val layoutId: Int = R.layout.fragment_user_profile

    lateinit var viewModelFactory: APIViewModelFactory
    lateinit var userDataViewModel: UserDataViewModel

    override fun init() {
        initViewModel()
        setUpObserver()
        getUserData()
    }

    private fun initViewModel() {
        viewModelFactory = APIViewModelFactory(solvedAcAPIRepository())
        userDataViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(UserDataViewModel::class.java)
    }

    private fun setUpObserver() {
        userDataViewModel.getUserDataRepositories.observe(viewLifecycleOwner) {
            data ->
            viewDataBinding.model = data

            if(data.code != 200) {
                Toast.makeText(requireContext(), "NO USER", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserData() {
        val handle = arguments?.getString("handle")
        userDataViewModel.getUserData(handle.toString())
    }
}