package com.example.dagger2assignment.Model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dagger2assignment.Model.Post
import com.example.dagger2assignment.Model.PostDao

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}