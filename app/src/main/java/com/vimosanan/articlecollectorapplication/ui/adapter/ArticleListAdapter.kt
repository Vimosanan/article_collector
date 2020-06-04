package com.vimosanan.articlecollectorapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vimosanan.articlecollectorapplication.R
import com.vimosanan.articlecollectorapplication.app.DEFAULT_VALUE
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.service.model.toStringDate
import kotlinx.android.synthetic.main.cardview_article.view.*

class ArticleListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cardview_article,
                parent,
                false
            ), interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Article>) {
        differ.submitList(list)
    }

    class ArticleViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: Article) = with(itemView) {
            itemView.txtViewTitle.text = article.title ?: DEFAULT_VALUE
            itemView.txtViewDate.text = if (article.lastUpdate != null) {
                toStringDate(article.lastUpdate)
            } else {
                DEFAULT_VALUE
            }
            itemView.txtViewDescription.text = article.shortDescription ?: DEFAULT_VALUE

            Glide.with(this)
                .load(article.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.test_avatar)
                .error(R.drawable.test_avatar)
                .into(itemView.imgViewAvatar)

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, article)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Article)
    }
}