package com.vivekupasani.foodvilla

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vivekupasani.foodvilla.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        1700 lines of code
        val navController = Navigation.findNavController(this, R.id.host_Fragment)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}