package com.practice.mvvmsolvedapi.ui

import android.os.Bundle
import android.view.Gravity.apply
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat.apply
import com.practice.mvvmsolvedapi.R
import com.practice.mvvmsolvedapi.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val layoutId: Int = R.layout.fragment_main

    override fun init() {
        viewDataBinding.handle = ""

        setUpBtnListener()
    }

    private fun setUpBtnListener() {
        viewDataBinding.searchBtn.setOnClickListener {
            val fragment = childFragmentManager.findFragmentById(
                viewDataBinding.userProfileContainer.id
            ) as UserProfileFragment?

            if(fragment == null) {
                val bundle = Bundle().apply {
                    putString("handle", viewDataBinding.handle)
                }

                childFragmentManager
                    .beginTransaction()
                    .replace(viewDataBinding.userProfileContainer.id,
                        UserProfileFragment().apply { arguments = bundle })
                    .commit()
            } else {
                fragment.userDataViewModel.getUserData(viewDataBinding.handle.toString())
            }
        }
    }
}