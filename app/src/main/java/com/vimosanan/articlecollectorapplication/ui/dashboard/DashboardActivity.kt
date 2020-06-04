package com.vimosanan.articlecollectorapplication.ui.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vimosanan.articlecollectorapplication.R
import com.vimosanan.articlecollectorapplication.app.ARTICLE
import com.vimosanan.articlecollectorapplication.app.ArticleCollectionApplication
import com.vimosanan.articlecollectorapplication.databinding.ActivityDashboardBinding
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.ui.ArticleViewModel
import com.vimosanan.articlecollectorapplication.ui.adapter.ArticleListAdapter
import com.vimosanan.articlecollectorapplication.ui.detail.DetailActivity
import com.vimosanan.articlecollectorapplication.util.Result
import javax.inject.Inject

class DashboardActivity : AppCompatActivity(), ArticleListAdapter.Interaction {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articlesAdapter: ArticleListAdapter
    private var mutableList = mutableListOf<Article>()
    private var mutableListTemp = mutableListOf<Article>()

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
        articleViewModel.loadAllArticles()
    }

    private fun initObservers() {
        articleViewModel.articles.observe(this, Observer {
            when (it) {
                is Result.Loading -> showProgress()
                is Result.Success -> {
                    hideProgress()
                    mutableList = it.data.toMutableList()
                    articlesAdapter.submitList(it.data)
                }
                is Result.Error -> {
                    hideProgress()
                    updateError(it.exception.message!!)
                }

            }
        })
    }

    override fun onItemSelected(position: Int, article: Article) {
        startActivityForResult(
            Intent(this, DetailActivity::class.java)
            .apply {
                putExtra(ARTICLE, article)
            }, REQUEST_CODE_ARTICLE
        )
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                REQUEST_CODE_ARTICLE -> {
                    val article = data?.getParcelableExtra<Article>(ARTICLE)
                    mutableList.forEach {
                        if(it.id == article?.id) {
                            mutableListTemp.add(article)
                        } else {
                            mutableListTemp.add(it)
                        }
                    }
                    mutableList = mutableListTemp
                    mutableListTemp = mutableListOf()
                    articlesAdapter.submitList(mutableList as List<Article>)
                }
            }
        }
        if(resultCode == Activity.RESULT_CANCELED){
            when (requestCode) {
                REQUEST_CODE_ARTICLE -> {
                    // do nothing
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_ARTICLE = 900
    }

    private fun showProgress() {
        binding.progressLoader.constraint.visibility = View.VISIBLE
        binding.progressLoader.animationView.visibility = View.VISIBLE
        binding.progressLoader.animationView.playAnimation()
    }

    private fun hideProgress() {
        binding.progressLoader.constraint.visibility = View.INVISIBLE
        binding.progressLoader.constraint.visibility = View.INVISIBLE
        binding.progressLoader.animationView.pauseAnimation()
    }

    private fun updateError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}