package com.example.submission2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.ui.profile.ProfileFragment
import com.example.submission2.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Digunakan untuk aktivasi button navigation
        val navView: BottomNavigationView = binding.navView

        /*
        inisiasi sebagai wadah fagment lain
        NavHostFragment, yaitu salah satu komponen dari Navigation yang berfungsi sebagai host
        dari fragment-fragment yang lainnya. Di sini lah letak fragment akan ditempelkan.
         */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerFragmentLain) as NavHostFragment

        val navController = navHostFragment.navController

        //setupWithNavController digunakan supaya Bottom Navigation menampilkan Fragment yang sesuai ketika menu dipilih.
        NavigationUI.setupWithNavController(navView, navController)


        val menu : Menu = navView.menu
        selectedMenu(menu.getItem(0))

        navView.setOnItemSelectedListener {
            item : MenuItem -> selectedMenu(item)

            false
        }
    }

    //Ketika buttom navigation diklik akan mengarah ke fragment masing"
    private fun selectedMenu(item : MenuItem){
        item.isChecked = true
        when(item.itemId){
            R.id.navigation_search ->
                selectedFragment(SearchFragment.getInstance())
            R.id.navigation_profile ->
                selectedFragment(ProfileFragment.getInstance())
        }
    }

    //fungsi yang digunakan untuk berpindah fragment
    private fun selectedFragment(fragment: Fragment) {
        val transaction : FragmentTransaction= supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
    }
}