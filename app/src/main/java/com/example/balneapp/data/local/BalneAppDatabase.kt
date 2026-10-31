package com.example.balneapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritoEntity::class, ReservaEntity::class, EquipoReservaEntity::class],
    version = 1,
    exportSchema = true
)
abstract class BalneAppDatabase : RoomDatabase() {
    abstract fun favoritoDao(): FavoritoDao
    abstract fun reservaDao(): ReservaDao
}
