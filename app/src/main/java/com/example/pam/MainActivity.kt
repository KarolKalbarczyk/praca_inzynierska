package com.example.pam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavHost

class MainActivity : AppCompatActivity(), NavHost {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getNavController(): NavController = NavController(this)

    /*private lateinit var navController: NavController
   private lateinit var appBarConfiguration: AppBarConfiguration


   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

       val navHostFragment = supportFragmentManager.findFragmentById(
           R.id.nav_host_fragment_container
       ) as NavHostFragment
       navController = navHostFragment.navController


       val appBarConfiguration = AppBarConfiguration(setOf(
           R.id.Redirect))

       setupActionBarWithNavController(navController, appBarConfiguration)
   }

   override fun onSupportNavigateUp(): Boolean {
       return navController.navigateUp(appBarConfiguration)
   }*/
}