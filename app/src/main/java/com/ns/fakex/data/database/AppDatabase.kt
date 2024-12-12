package com.ns.fakex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ns.fakex.data.model.TweetEntity

@Database(entities = [TweetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tweetsDao(): TweetsDao
}
