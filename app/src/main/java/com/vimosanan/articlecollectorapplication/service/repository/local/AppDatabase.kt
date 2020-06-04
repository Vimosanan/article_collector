package com.vimosanan.articlecollectorapplication.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vimosanan.articlecollectorapplication.service.model.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        )
                            .also { INSTANCE = it }
                }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "Sample.db"
            )
                //pre-populate the database after onCreate was called when needed
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                //.fallbackToDestructiveMigration() // when we don't care about the database to be cleared
                .addMigrations()
                .build()

    }
}