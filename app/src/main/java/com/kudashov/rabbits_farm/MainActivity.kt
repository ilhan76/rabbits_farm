package com.kudashov.rabbits_farm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kudashov.rabbits_farm.databinding.ActivityMainBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class MainActivity : AppCompatActivity() {

    lateinit var mNavController: NavController
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}