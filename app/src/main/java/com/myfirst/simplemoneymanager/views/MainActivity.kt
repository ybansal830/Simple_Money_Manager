package com.myfirst.simplemoneymanager.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.myfirst.simplemoneymanager.R
import com.myfirst.simplemoneymanager.views.adapter.FragmentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener {
            startActivity(Intent(this, AddEditItemActivity::class.java))
        }

        viewPager.adapter = FragmentAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(tabLayout,viewPager,object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                when(position){
                    0 -> tab.text = "Income"
                    1 -> tab.text = "Expense"
                    2 -> tab.text = "Balance"
                }
            }
        }).attach()

    }
}