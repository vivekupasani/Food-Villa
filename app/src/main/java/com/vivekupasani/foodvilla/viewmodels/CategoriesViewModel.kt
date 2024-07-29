package com.vivekupasani.foodvilla.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vivekupasani.foodvilla.models.Category
import com.vivekupasani.foodvilla.models.CategoryList
import com.vivekupasani.foodvilla.models.CategoryMeals
import com.vivekupasani.foodvilla.models.CategoryNames
import com.vivekupasani.foodvilla.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    private var CategoriesLiveData = MutableLiveData<List<CategoryMeals>>()

    fun getCategories(categoryName : String){
        RetrofitInstance.foodApi.getCategories(categoryName).enqueue(object : Callback<CategoryList?> {
            override fun onResponse(p0: Call<CategoryList?>, p1: Response<CategoryList?>) {
                if (p1.body() != null){
                    CategoriesLiveData.value = p1.body()!!.meals
                }
            }

            override fun onFailure(p0: Call<CategoryList?>, p1: Throwable) {
                Log.e("Home", "Failed to fetch Categories", p1)
            }
        })
    }

    fun observeCategoriesLiveData() : LiveData<List<CategoryMeals>>{
        return CategoriesLiveData
    }
}