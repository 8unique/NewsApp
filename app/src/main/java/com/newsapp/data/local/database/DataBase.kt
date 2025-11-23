package com.newsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsapp.data.local.dao.NewsDao
import com.newsapp.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}