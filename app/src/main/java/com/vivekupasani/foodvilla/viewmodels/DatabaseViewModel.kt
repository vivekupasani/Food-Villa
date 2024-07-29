package com.vivekupasani.foodvilla.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.vivekupasani.foodvilla.database.mealDatabase
import com.vivekupasani.foodvilla.models.Meal
import com.vivekupasani.foodvilla.repository.DatabaseRepository

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {

     val repository: DatabaseRepository
    private val getMealLiveData: LiveData<List<Meal>>

    init {
        val dao = mealDatabase.getDatabaseInstance(application).dao()
        repository = DatabaseRepository(dao)
        getMealLiveData = repository.getMeal()
    }

    fun observeGetMealLiveData(): LiveData<List<Meal>> {
        return getMealLiveData
    }

    fun addMeal(meal: Meal) {
        repository.upserMeal(meal)
    }

    fun deleteMeal(meal: Meal) {
        repository.deleteMeal(meal)
    }

    fun getMeal(): LiveData<List<Meal>> {
        return repository.getMeal()
    }
}
