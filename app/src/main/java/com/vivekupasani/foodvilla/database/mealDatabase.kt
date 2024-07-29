package com.vivekupasani.foodvilla.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vivekupasani.foodvilla.dao.MealDao
import com.vivekupasani.foodvilla.models.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(TypeConveter::class)
abstract class mealDatabase : RoomDatabase() {

    abstract fun dao(): MealDao

    companion object {
        @Volatile
        private var instance: mealDatabase? = null

        fun getDatabaseInstance(context: Context): mealDatabase {
            var temp = instance
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val database = Room.databaseBuilder(context, mealDatabase::class.java, "FoodVilla")
                    .allowMainThreadQueries()
                    .build()
                instance = database
                return database
            }
        }
    }
}