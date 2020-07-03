package com.simonkim.toynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simonkim.toynews.R
import com.simonkim.toynews.module.URLToImage
import java.net.URL

class ArticleRecyclerViewAdapter(val context: Context, val articleList: ArrayList<Article>) : RecyclerView.Adapter<ArticleRecyclerViewAdapter.articleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): articleHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_article, parent, false)
        return articleHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: articleHolder, position: Int) {
        holder.bind(articleList[position], context)
    }

    inner class articleHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var articleImage = itemView?.findViewById<ImageView>(R.id.articleImage)
        var articleTitle = itemView?.findViewById<TextView>(R.id.articleTitle)
        var articleSource = itemView?.findViewById<TextView>(R.id.articleSource)
        var articleContent = itemView?.findViewById<TextView>(R.id.articleContent)

        @SuppressLint("SetTextI18n")
        fun bind (article: Article, context: Context) {
//          // TODO(URL에서 이미지받아와서 뿌려주기)
            articleTitle?.text = article.title
            articleSource?.text = "Source by " + article.source
            articleContent?.text = article.description
        }
    }
}