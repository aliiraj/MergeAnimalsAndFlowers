package com.example.mergeanimalsandflowers.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mergeanimalsandflowers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavController
import androidx.room.util.StringUtil
import com.example.mergeanimalsandflowers.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navController = navHostFragment.navController

        handleToolbar()
    }

    private fun handleToolbar() {
        setSupportActionBar(binding.toolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // for font
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

}