package com.ns.fakex.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ns.fakex.data.model.TweetEntity

@Dao
interface TweetsDao {
    @Query("SELECT COUNT(*) FROM tweet_items")
    suspend fun getTweetCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTweets(tweets: List<TweetEntity>)

    @Query("SELECT * FROM tweet_items")
    suspend fun getAllTweets(): List<TweetEntity>

    @Query("DELETE FROM tweet_items")
    suspend fun deleteAllTweets()

    @Query("DELETE FROM tweet_items WHERE id IN (SELECT id FROM tweet_items ORDER BY id ASC LIMIT :limit)")
    suspend fun deleteOldestTweets(limit: Int)
}