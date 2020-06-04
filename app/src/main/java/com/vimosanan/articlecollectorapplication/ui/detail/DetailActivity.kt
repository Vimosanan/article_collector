package com.vimosanan.articlecollectorapplication.ui.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vimosanan.articlecollectorapplication.R
import com.vimosanan.articlecollectorapplication.app.ARTICLE
import com.vimosanan.articlecollectorapplication.app.ArticleCollectionApplication
import com.vimosanan.articlecollectorapplication.app.DEFAULT_VALUE
import com.vimosanan.articlecollectorapplication.databinding.ActivityDetailBinding
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.ui.ArticleViewModel
import com.vimosanan.articlecollectorapplication.ui.edit.EditArticleActivity
import com.vimosanan.articlecollectorapplication.util.Result
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityDetailBinding
    private lateinit var articleViewModel: ArticleViewModel
    private var article: Article? = null
    private var edited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as ArticleCollectionApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showProgress()

        // register for view-model
        articleViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(ArticleViewModel::class.java)

        article = intent.getParcelableExtra(ARTICLE)

        if (article != null) {
            articleViewModel.loadDescriptionData(article!!.id)
        } else {
            returnBackToDashboard()
        }

        initObservers()
    }

    private fun initObservers() {
        articleViewModel.article.observe(this, Observer {
            when (it) {
                is Result.Loading -> showProgress()
                is Result.Success -> {
                    hideProgress()
                    this.article?.description = it.data.description ?: DEFAULT_VALUE
                    updateView()
                }
                is Result.Error -> {
                    hideProgress()
                    updateError(it.exception.message!!)
                }
            }
        })

        binding.txtViewEdit.setOnClickListener {
            startActivityForResult(
                Intent(this, EditArticleActivity::class.java)
                    .apply {
                        putExtra(ARTICLE, article)
                    }, REQUEST_CODE_EDIT_ARTICLE
            )
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
        }

        binding.txtViewBack.setOnClickListener {
            returnBackToDashboard()
        }
    }


    private fun updateView() {
        binding.txtViewTitle.text = this.article?.title ?: DEFAULT_VALUE

        Glide.with(this)
            .load(this.article?.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.test_avatar)
            .error(R.drawable.test_avatar)
            .into(binding.imgViewAvatar)

        binding.txtViewDescription.text = this.article?.description ?: DEFAULT_VALUE

        hideProgress()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                REQUEST_CODE_EDIT_ARTICLE -> {
                    article = data?.getParcelableExtra(ARTICLE)
                    updateView()
                    edited = true
                }
            }
        }
        if(resultCode == Activity.RESULT_CANCELED){
            when (requestCode) {
                REQUEST_CODE_EDIT_ARTICLE -> {
                    // do nothing
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_EDIT_ARTICLE = 901
    }

    override fun onBackPressed() {
        returnBackToDashboard()
    }

    private fun returnBackToDashboard() {
        if(edited) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(ARTICLE, article)
            })
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
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