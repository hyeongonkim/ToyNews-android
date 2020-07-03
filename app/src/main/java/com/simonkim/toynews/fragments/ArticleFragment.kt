package com.simonkim.toynews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simonkim.toynews.MainActivity
import com.simonkim.toynews.R
import com.simonkim.toynews.adapter.Article
import com.simonkim.toynews.adapter.ArticleRecyclerViewAdapter

class ArticleFragment : Fragment() {
    var articleList = arrayListOf<Article>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)

        // TODO(샘플데이터이니 추후 지울 것)
        articleList.add(Article("https://www.businesswire.com/news/home/20200702005632/en/Northern-Data-announces-new-Block.one-backed-customer", "http://www.businesswire.com/images/bwlogo_square.png", "Business Wire", "Northern Data announces new Block.one backed customer", "FRANKFURT AM MAIN, Germany--(BUSINESS WIRE)--Northern Data AG (XETRA: NB2, ISIN: DE000A0SMU87), one of the world's largest providers of high-performance computing (HPC) solutions, continues to enjoy a very successful 2020 with the signing of 180 MW of capacit…"))

        recyclerView = rootView.findViewById(R.id.articleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ArticleRecyclerViewAdapter(requireContext(), articleList)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}