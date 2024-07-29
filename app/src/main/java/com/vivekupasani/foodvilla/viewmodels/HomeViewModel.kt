package com.vivekupasani.foodvilla.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vivekupasani.foodvilla.models.*
import com.vivekupasani.foodvilla.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    // Random Meal Api Call
    private val randomMealLiveData = MutableLiveData<Meal>()
    fun getRandomMeal() {
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<RandomMealResponse?> {
            override fun onResponse(
                call: Call<RandomMealResponse?>, response: Response<RandomMealResponse?>
            ) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<RandomMealResponse?>, p1: Throwable) {
                Log.e("Home", "Failed to fetch random meal", p1)
            }
        })
    }

    fun observeRandomMeal(): LiveData<Meal> {
        return randomMealLiveData
    }


    // Popular meal Api Call
    private val popularItemLiveData = MutableLiveData<List<CategoryMeals>>()
    fun getPopularItems() {
        RetrofitInstance.foodApi.getPopularItems("Seafood")
            .enqueue(object : Callback<CategoryList?> {
                override fun onResponse(p0: Call<CategoryList?>, p1: Response<CategoryList?>) {
                    if (p1.body() != null) {
                        popularItemLiveData.value = p1.body()!!.meals
                    }
                }

                override fun onFailure(p0: Call<CategoryList?>, p1: Throwable) {
                    Log.e("Home", "Failed to fetch popular meal", p1)
                }
            })
    }

    fun observePopularItems(): LiveData<List<CategoryMeals>> {
        return popularItemLiveData
    }


    // Category Api Call
    private val categoryLiveData = MutableLiveData<List<Category>>()
    fun getCategory() {
        RetrofitInstance.foodApi.getCategory().enqueue(object : Callback<CategoryNames?> {
            override fun onResponse(p0: Call<CategoryNames?>, p1: Response<CategoryNames?>) {
                if (p1.body() != null) {
                    categoryLiveData.value = p1.body()!!.categories
                }
            }

            override fun onFailure(p0: Call<CategoryNames?>, p1: Throwable) {
                Log.e("Home", "Failed to fetch Category", p1)
            }
        })
    }

    fun observeCategoryLiveData(): LiveData<List<Category>> {
        return categoryLiveData
    }
}
