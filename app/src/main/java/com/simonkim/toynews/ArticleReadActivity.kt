package com.simonkim.toynews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.simonkim.toynews.realm.RealmScrapObject
import io.realm.Realm

class ArticleReadActivity : AppCompatActivity() {

    val defaultRealm = Realm.getDefaultInstance()

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
            scrapArticle()
        }

        setTitle("News Article")
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun scrapArticle() {
        defaultRealm.beginTransaction()
        val url = intent.getStringExtra("url")
        if (defaultRealm.where(RealmScrapObject::class.java).equalTo("url", url).findFirst() == null) {
            val newArticle = defaultRealm.createObject(RealmScrapObject::class.java, url)
            newArticle.urlToImage = intent.getStringExtra("urlToImage")
            newArticle.title = intent.getStringExtra("title")
            newArticle.description = intent.getStringExtra("description")
            Toast.makeText(this, "뉴스 기사를 스크랩했어요!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "이미 스크랩한 기사에요", Toast.LENGTH_SHORT).show()
        }
        defaultRealm.commitTransaction()
    }
}