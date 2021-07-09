package com.kudashov.rabbits_farm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kudashov.rabbits_farm.databinding.ActivityMainBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class MainActivity : AppCompatActivity() {

    private var TAG: String = this::class.java.simpleName
    lateinit var mNavController: NavController
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottomNavigationView = mBinding.footerBar
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setupWithNavController(navController = mNavController)
    }

    fun hideBottomNavigation(){ mBinding.footerBar.visibility = View.GONE }
    fun showBottomNavigation(){ mBinding.footerBar.visibility = View.VISIBLE }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}