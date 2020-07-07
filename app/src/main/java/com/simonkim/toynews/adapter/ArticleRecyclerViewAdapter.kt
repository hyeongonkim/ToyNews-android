package com.simonkim.toynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simonkim.toynews.R
import com.simonkim.toynews.data.Article

class ArticleRecyclerViewAdapter(val context: Context, var articleList: List<Article>, val itemClick: (Article) -> Unit, val itemLongClick: (Article) -> Boolean) : RecyclerView.Adapter<ArticleRecyclerViewAdapter.articleHolder>() {

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
        var articleImage = itemView!!.findViewById<ImageView>(R.id.articleImage)
        var articleTitle = itemView!!.findViewById<TextView>(R.id.articleTitle)
        var articleContent = itemView!!.findViewById<TextView>(R.id.articleContent)

        @SuppressLint("SetTextI18n")
        fun bind (article: Article, context: Context) {
            Glide.with(context)
                .load(article.urlToImage)
                .into(articleImage)
            articleTitle.text = article.title
            articleContent.text = article.description

            itemView.setOnClickListener { itemClick(article) }
            itemView.setOnLongClickListener { itemLongClick(article) }
        }
    }
}