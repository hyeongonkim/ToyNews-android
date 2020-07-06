package com.simonkim.toynews

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ArticleReadActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articleread)

        val articleWebView = findViewById<View>(R.id.articleWebView) as WebView

        WebView.setWebContentsDebuggingEnabled(true)

        val settings = articleWebView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        articleWebView.loadUrl(intent.getStringExtra("url"))

        val fab: View = findViewById<View>(R.id.scrap_fab)
        fab.setOnClickListener { view ->
            Toast.makeText(this, "뉴스 기사를 스크랩했어요!", Toast.LENGTH_SHORT).show()
        }

        setTitle("News Article")
    }
}