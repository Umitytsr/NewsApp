package com.umitytsr.myapplication.ui.news

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.umitytsr.myapplication.R
import com.umitytsr.myapplication.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController


        NavigationUI.setupWithNavController(binding.bottomNavView, navController)

        val bottomNavFragments = setOf(R.id.homeFragment, R.id.searchFragment, R.id.favoriteFragment,R.id.profileFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in bottomNavFragments) {
                binding.bottomNavView.visibility = View.VISIBLE
            } else {
                binding.bottomNavView.visibility = View.GONE
            }
        }
    }
}