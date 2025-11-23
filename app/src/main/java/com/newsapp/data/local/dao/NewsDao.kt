package com.newsapp.data.local.dao

import androidx.room.*
import com.newsapp.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE category = :category ORDER BY publishedAt DESC")
    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM articles WHERE isFavorite = 1 ORDER BY publishedAt DESC")
    fun getFavoriteArticles(): Flow<List<ArticleEntity>>

    @Query("UPDATE articles SET isFavorite = :isFavorite WHERE url = :url")
    suspend fun updateFavorite(url: String, isFavorite: Boolean)

    @Query("SELECT * FROM articles WHERE url = :url LIMIT 1")
    fun getArticle(url: String): Flow<ArticleEntity?>

}