package com.vivekupasani.foodvilla.repository

import androidx.lifecycle.LiveData
import com.vivekupasani.foodvilla.dao.MealDao
import com.vivekupasani.foodvilla.models.Meal

class DatabaseRepository(val dao: MealDao) {

    fun getMeal(): LiveData<List<Meal>> = dao.getAllMeal()

    fun upserMeal(meal: Meal) {
        return dao.upsert(meal)
    }

    fun deleteMeal(meal: Meal) {
        return dao.delete(meal)
    }
}