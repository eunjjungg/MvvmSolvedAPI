package com.practice.mvvmsolvedapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practice.mvvmsolvedapi.R
import com.practice.mvvmsolvedapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        showMainFragment()
    }

    private fun showMainFragment() {
        supportFragmentManager.beginTransaction().replace(binding.mainContainer.id, MainFragment()).commit()
    }
}