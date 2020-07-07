package com.simonkim.toynews.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simonkim.toynews.ArticleReadActivity
import com.simonkim.toynews.R
import com.simonkim.toynews.adapter.ArticleRecyclerViewAdapter
import com.simonkim.toynews.data.Article
import com.simonkim.toynews.realm.RealmScrapObject
import io.realm.Realm
import org.jetbrains.anko.support.v4.startActivity

class ScrappedFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleRecyclerViewAdapter
    val defaultRealm: Realm = Realm.getDefaultInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_scrapped, container, false)

        recyclerView = rootView.findViewById(R.id.scrappedRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ArticleRecyclerViewAdapter(requireContext(), emptyList())
        recyclerView.adapter = adapter

        adapter.setItemClickListener( object : ArticleRecyclerViewAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                startActivity<ArticleReadActivity>(
                    "url" to adapter.articleList[position].url,
                    "urlToImage" to adapter.articleList[position].urlToImage,
                    "title" to adapter.articleList[position].title,
                    "description" to adapter.articleList[position].description)
            }
        })

        getScrappedArticle()

        return rootView
    }

    fun getScrappedArticle() {
        defaultRealm.beginTransaction()
        val snapshot = defaultRealm.where(RealmScrapObject::class.java).findAll()
        defaultRealm.commitTransaction()
        val articleList : ArrayList<Article> = arrayListOf()
        for (i in snapshot) {
            articleList.add(Article(i.url, i.urlToImage, i.title, i.description))
        }
        adapter.articleList = articleList
        adapter.notifyDataSetChanged()
    }
}