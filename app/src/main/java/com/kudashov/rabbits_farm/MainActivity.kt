package com.kudashov.rabbits_farm

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kudashov.rabbits_farm.databinding.ActivityMainBinding
import com.kudashov.rabbits_farm.screens.auth.delegate.AuthNavigationDelegate
import com.kudashov.rabbits_farm.utilits.const.*

class MainActivity : AppCompatActivity(),
    AuthNavigationDelegate {

    private var TAG: String = this::class.java.simpleName
    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashTheme)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        APP_ACTIVITY = this
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottomNavigationView = binding.footerBar
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setupWithNavController(navController = navController)

        bottomNavigationView.setOnItemSelectedListener {
            underlineSelectedItem(it)
            true
        }
    }

    private fun underlineSelectedItem(item: MenuItem?) {
        if (item == null) return

        val transition: Transition = ChangeBounds()

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition?) {}
            override fun onTransitionEnd(transition: Transition?) {
                NavigationUI.onNavDestinationSelected(item, navController)
            }

            override fun onTransitionCancel(transition: Transition?) {}
            override fun onTransitionPause(transition: Transition?) {}
            override fun onTransitionResume(transition: Transition?) {}
        })
        TransitionManager.beginDelayedTransition(binding.main, transition)
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.main)
        constraintSet.setHorizontalBias(R.id.underline, getItemPosition(item.itemId) * 0.5f)
        constraintSet.applyTo(binding.main)
    }

    private fun getItemPosition(itemId: Int): Int {
        return when (itemId) {
            R.id.farm -> 0
            R.id.tasks -> 1
            R.id.birth -> 2
            else -> 0
        }
    }

    fun moveUnderline(itemId: Int) {
        val transition: Transition = ChangeBounds()

        TransitionManager.beginDelayedTransition(binding.main, transition)

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.main)
        constraintSet.setHorizontalBias(R.id.underline, getItemPosition(itemId) * 0.5f)
        constraintSet.applyTo(binding.main)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun logIn() {
        binding.footerBar.visibility = View.VISIBLE
        binding.underline.visibility = View.VISIBLE
        navController.navigate(R.id.action_auth_to_farm)
    }

    override fun logOut() {
        binding.footerBar.visibility = View.GONE
        binding.underline.visibility = View.GONE

        val pref: SharedPreferences = this.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()

        editor.apply {
            putString(USER_TOKEN, "")
        }
        editor.apply()
        navController.navigate(R.id.action_farm_to_auth)
    }


    fun showLoader(){
        binding.loadPage.visibility = View.VISIBLE
    }

    fun hideLoader(){
        binding.loadPage.visibility = View.GONE
    }
}