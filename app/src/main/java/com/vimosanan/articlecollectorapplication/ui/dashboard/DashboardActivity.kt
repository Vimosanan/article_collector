package com.vimosanan.articlecollectorapplication.ui.dashboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vimosanan.articlecollectorapplication.app.ArticleCollectionApplication
import com.vimosanan.articlecollectorapplication.databinding.ActivityDashboardBinding
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.ui.ArticleViewModel
import com.vimosanan.articlecollectorapplication.ui.adapter.ArticleListAdapter
import javax.inject.Inject

class DashboardActivity : AppCompatActivity(), ArticleListAdapter.Interaction {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articlesAdapter: ArticleListAdapter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as ArticleCollectionApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // register for view-model
        articleViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(ArticleViewModel::class.java)

        //init adapters
        articlesAdapter = ArticleListAdapter(this)

        //init recycler view and set adapter
        val viewManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        binding.recyclerView.adapter = articlesAdapter

        binding.recyclerView.apply {
            setHasFixedSize(false)

            layoutManager = viewManager

            adapter = articlesAdapter
        }

        initObservers()
    }

    private fun initObservers() {

    }

    override fun onItemSelected(position: Int, article: Article) {

    }
}