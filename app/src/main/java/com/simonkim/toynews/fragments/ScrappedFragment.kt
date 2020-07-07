package com.simonkim.toynews.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
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

        adapter = ArticleRecyclerViewAdapter(requireContext(), emptyList(),
            { article ->
                startActivity<ArticleReadActivity>(
                    "url" to article.url,
                    "urlToImage" to article.urlToImage,
                    "title" to article.title,
                    "description" to article.description)
            }, { article ->
                deleteScrappedArticleDialog(article.url)
                true
            })
        recyclerView.adapter = adapter

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

    fun deleteScrappedArticleDialog(url: String) {
        val deleteDialog = AlertDialog.Builder(context)
        deleteDialog.setTitle("스크랩 해제")
        deleteDialog.setMessage("해당 기사의 스크랩을 해제하시겠어요?")

        val dialogListener = object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                when(p1) {
                    DialogInterface.BUTTON_POSITIVE ->
                        deleteArticleFromRealm(url)
                }
            }
        }

        deleteDialog.setPositiveButton("해제", dialogListener)
        deleteDialog.setNegativeButton("닫기", null)
        deleteDialog.show()
    }

    fun deleteArticleFromRealm(url: String) {
        defaultRealm.beginTransaction()
        val target = defaultRealm.where(RealmScrapObject::class.java).equalTo("url", url).findFirst()
        val deletedTitle = target?.title
        target?.deleteFromRealm()
        defaultRealm.commitTransaction()
        getScrappedArticle()
        Toast.makeText(context, "$deletedTitle\n기사의 스크랩을 해제했어요", Toast.LENGTH_SHORT).show()
    }
}