package com.vimosanan.articlecollectorapplication.ui.edit

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vimosanan.articlecollectorapplication.R
import com.vimosanan.articlecollectorapplication.app.ARTICLE
import com.vimosanan.articlecollectorapplication.app.ArticleCollectionApplication
import com.vimosanan.articlecollectorapplication.app.DEFAULT_VALUE
import com.vimosanan.articlecollectorapplication.databinding.ActivityDetailBinding
import com.vimosanan.articlecollectorapplication.databinding.ActivityEditArticleBinding
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.ui.ArticleViewModel
import com.vimosanan.articlecollectorapplication.util.Result
import kotlinx.android.synthetic.main.activity_edit_article.*
import javax.inject.Inject

class EditArticleActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityEditArticleBinding
    private lateinit var articleViewModel: ArticleViewModel
    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as ArticleCollectionApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityEditArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showProgress()

        // register for view-model
        articleViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(ArticleViewModel::class.java)

        article = intent.getParcelableExtra(ARTICLE)

        if (article != null) {
            updateView()
        } else {
            updateError("Article is not fetched")
        }

        initObservers()
    }


    private fun initObservers() {
        binding.txtViewCancel.setOnClickListener {
            checkOnBackStage()
        }

        binding.btnSave.setOnClickListener {
            saveArticle()
        }

        articleViewModel.article.observe(this, Observer {
            when(it) {
                is Result.Loading -> showProgress()
                is Result.Success -> {
                    backToDetailPage(edited = true)
                }
                is Result.Error -> {
                    hideProgress()
                    updateError(it.exception.message!!)
                }
            }
        })
    }

    private fun saveArticle() {
        val title = edtTextTitle.text.toString().trim()
        val description = edtTextDescription.text.toString().trim()

        if (title.isEmpty() || title.isBlank() || description.isBlank() || description.isEmpty()) {
            showToast("Title, Description cannot be empty!")
        } else {
            showProgress()
            article?.title = title
            article?.description = description
            articleViewModel.saveArticleToDatabase(article!!)
        }

    }

    private fun checkOnBackStage() {
        val title = edtTextTitle.text.toString().trim()
        val description = edtTextDescription.text.toString().trim()
        if (article?.title?.trim() != title || article?.description?.trim() != description) {
            showDialog()
        } else {
            backToDetailPage(edited = false)
        }
    }

    private fun showDialog(){
        lateinit var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Are you sure?")

        builder.setMessage("You haven't save the changes. Quit without saving?!!!")

        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> backToDetailPage(edited = false)
                DialogInterface.BUTTON_NEGATIVE -> saveArticle()
                DialogInterface.BUTTON_NEUTRAL -> { } // do nothing
            }
        }

        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("NO",dialogClickListener)
        builder.setNeutralButton("CANCEL",dialogClickListener)

        dialog = builder.create()

        dialog.show()
    }

    private fun backToDetailPage(edited: Boolean) {
        if (edited) {
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

    private fun updateView() {
        binding.edtTextTitle.text = SpannableStringBuilder(article?.title ?: DEFAULT_VALUE)
        binding.edtTextDescription.text =
            SpannableStringBuilder(article?.description ?: DEFAULT_VALUE)

        hideProgress()
    }

    override fun onBackPressed() {
        checkOnBackStage()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun updateError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}