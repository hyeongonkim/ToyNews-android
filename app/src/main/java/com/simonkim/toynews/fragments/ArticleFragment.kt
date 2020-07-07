package com.simonkim.toynews.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simonkim.toynews.ArticleReadActivity
import com.simonkim.toynews.R
import com.simonkim.toynews.adapter.ArticleRecyclerViewAdapter
import com.simonkim.toynews.data.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleRecyclerViewAdapter
    val BASEURL = "https://newsapi.org/"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)

        recyclerView = rootView.findViewById(R.id.articleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ArticleRecyclerViewAdapter(requireContext(), emptyList(),
            { article ->
                startActivity<ArticleReadActivity>(
                    "url" to article.url,
                    "urlToImage" to article.urlToImage,
                    "title" to article.title,
                    "description" to article.description)
            }, { article -> true })
        recyclerView.adapter = adapter

        getHeadlines()

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_view, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query != "") {
                    getNewsResponse(query)
                } else if (query == "") {
                    getHeadlines()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != "") {
                    getNewsResponse(newText)
                } else if (newText == "") {
                    getHeadlines()
                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_search -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    fun getHeadlines() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val articleRequest = retrofit.create(ArticleRequest::class.java)
        val call = articleRequest.getHeadlines("us", NewsapiKey.key)
        call.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if(response.isSuccessful) {
                    adapter.articleList = response.body()?.articles!!
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getNewsResponse(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val articleRequest = retrofit.create(ArticleRequest::class.java)
        val call = articleRequest.getNewsResponse(query, NewsapiKey.key)
        call.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if(response.isSuccessful) {
                    adapter.articleList = response.body()?.articles!!
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}