package com.simonkim.toynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simonkim.toynews.fragments.ArticleFragment
import com.simonkim.toynews.fragments.ScrappedFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<View>(R.id.navigationView) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ArticleFragment()).commit()

        setTitle("News Article")
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId) {
            R.id.newsItem -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ArticleFragment()).commit()
            }
            R.id.scrapItem -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ScrappedFragment()).commit()
            }
        }
        return true
    }
}