package com.vivekupasani.foodvilla.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vivekupasani.foodvilla.models.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(meal: Meal)

    @Delete
    fun delete(meal: Meal)

    @Query("SELECT * FROM FoodVilla")
    fun getAllMeal() : LiveData<List<Meal>>
}