package com.example.residente_app.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "residentApp.db"
                ).fallbackToDestructiveMigration().addCallback(object: RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase){
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            seedAdminUser(context)
                        }
                    }
                })
                    .build().also { INSTANCE = it }
            }

        private fun seedAdminUser(context: Context) {
            val dao = get(context).userDao()
            CoroutineScope(Dispatchers.IO).launch {
                val existing = dao.findUserByUsername("admin")
                if (existing == null) {
                    val admin = User(
                        username = "admin",
                        password = "admin123",
                        role = "admin"
                    )
                    dao.insertUser(admin)
                }
            }
        }
    }
}