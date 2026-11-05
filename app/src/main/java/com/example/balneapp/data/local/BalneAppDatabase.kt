package com.example.balneapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritoEntity::class, ReservaEntity::class, EquipoReservaEntity::class],
    version = 1,
    exportSchema = true
)
abstract class BalneAppDatabase : RoomDatabase() {
    abstract fun favoritoDao(): FavoritoDao
    abstract fun reservaDao(): ReservaDao

    companion object {
        @Volatile
        private var INSTANCE: BalneAppDatabase? = null

        fun obtenerInstancia(context: Context): BalneAppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BalneAppDatabase::class.java,
                    "balneapp_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}