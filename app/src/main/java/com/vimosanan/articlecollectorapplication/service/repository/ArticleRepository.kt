package com.vimosanan.articlecollectorapplication.service.repository

import android.os.Parcelable
import com.vimosanan.articlecollectorapplication.service.ApiInterface
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.util.Result
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val api: ApiInterface) {

    suspend fun loadAllArticlesFromRemote(): Result<List<Article>>{
        return Result.Success(results)
/*        val result = api.getAllArticles()

        return if(result.isSuccessful) {
            val data = result.body()

            if(data != null && data.isNotEmpty()) {
                Result.Success(data)
            } else {
                Result.Error(Exception("Data is null or empty..!"))
            }
        } else {
            Result.Error(Exception("Something went wrong..!"))
        }*/
    }

    suspend fun loadDescriptionFromRemote(id: Int): Result<Article>{
        return Result.Success(results[id -1])
        /*val result = api.getArticleDetail(id)

        return if(result.isSuccessful) {
            val data = result.body()

            if(data != null) {
                Result.Success(data)
            } else {
                Result.Error(Exception("Data is null or empty..!"))
            }
        } else {
            Result.Error(Exception("Something went wrong..!"))
        }*/
    }

    private var results: List<Article> = listOf(
        Article(
            1,
            "Article 1 sample title length is too long!",
            "1577836800",
            "1 This is article 1 long text. Sitting mistake towards his few country ask.",
            "1 This is article 1 long text. Sitting mistake towards his few country ask.",
            "This 1 is article 1 long text. Sitting mistake towards his few country ask."
        ),
        Article(
            2,
            "Article 2 sample title length is too long!",
            "1577836800",
            "1 This is article 1 long text. Sitting mistake towards his few country ask.",
            "",
            "This 2 is article 1 long text. Sitting mistake towards his few country ask."
        ),
        Article(
            3,
            "Article 3 sample title length is too long!",
            "1577836800",
            "3 This is article 1 long text. Sitting mistake towards his few country ask.",
            "This is article 1 long text. Sitting mistake towards his few country ask.",
            "This 3 is article 1 long text. Sitting mistake towards his few country ask."
        ),
        Article(
            4,
            "Article 4 sample title length is too long!",
            "1577836800",
            "4 This is article 1 long text. Sitting mistake towards his few country ask.",
            "This is article 1 long text. Sitting mistake towards his few country ask.",
            "This 4 is article 1 long text. Sitting mistake towards his few country ask."
        ),
        Article(
            5,
            "Article 5 sample title length is too long!",
            "1577836800",
            "5 This is article 1 long text. Sitting mistake towards his few country ask.",
            "This is article 1 long text. Sitting mistake towards his few country ask.",
            "This 5 is article 1 long text. Sitting mistake towards his few country ask."
        )
    )
}
