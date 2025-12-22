package com.example.mymovie.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors
import kotlin.concurrent.Volatile

// ВАРИАНТ 2: С базовыми настройками
@Database(
    entities = [User::class, Friendship::class, UserMovie::class],
    version = 1,
    exportSchema = false
)
abstract class MainDb : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun friendshipDao(): FriendshipDao
    abstract fun userMovieDao(): UserMovieDao

    companion object {
        @Volatile
        private var INSTANCE: MainDb? = null

        private const val DATABASE_NAME = "movie_app.db"

        fun getDatabase(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    DATABASE_NAME
                )
                    // Обязательно для разработки:
                    .fallbackToDestructiveMigration()

                    // Рекомендуется:
                     .addCallback(roomCallback)
                     .setQueryCallback({ sql, args ->
                         Log.d("RoomSQL", "SQL: $sql")
                     }, Executors.newSingleThreadExecutor())

                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Опционально: коллбек для инициализации
        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Инициализация при первом создании
            }
        }
    }
}