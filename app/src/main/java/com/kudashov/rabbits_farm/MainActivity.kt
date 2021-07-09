package com.kudashov.rabbits_farm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
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

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            underlineSelectedItem(item)
            true
        }
    }

    private fun underlineSelectedItem(item: MenuItem?) {
        if (item == null) return
        val transition: Transition = ChangeBounds()

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition?) {
            }
            override fun onTransitionEnd(transition: Transition?) {
                NavigationUI.onNavDestinationSelected(item, mNavController)
            }
            override fun onTransitionCancel(transition: Transition?) {
            }
            override fun onTransitionPause(transition: Transition?) {
            }
            override fun onTransitionResume(transition: Transition?) {
            }
        })

        TransitionManager.beginDelayedTransition(mBinding.main, transition)

        val constraintSet = ConstraintSet()
        constraintSet.clone(mBinding.main)
        constraintSet.setHorizontalBias(R.id.underline, getItemPosition(item.itemId) * 0.5f)
        constraintSet.applyTo(mBinding.main)
    }

    private fun getItemPosition(itemId: Int): Int {
        return when (itemId) {
            R.id.farm -> 0
            R.id.tasks -> 1
            R.id.birth -> 2
            else -> 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}