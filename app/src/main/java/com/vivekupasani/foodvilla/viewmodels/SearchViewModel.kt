package com.vivekupasani.foodvilla.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vivekupasani.foodvilla.models.Meal
import com.vivekupasani.foodvilla.models.RandomMealResponse
import com.vivekupasani.foodvilla.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val SearchCategoryLiveData = MutableLiveData<List<Meal>>()

    fun searchCategory(searchQuery: String) {
        RetrofitInstance.foodApi.searchCategory(searchQuery)
            .enqueue(object : Callback<RandomMealResponse?> {
                override fun onResponse(
                    p0: Call<RandomMealResponse?>,
                    p1: Response<RandomMealResponse?>
                ) {
                    if (p1.body() != null) {
                        SearchCategoryLiveData.postValue(p1.body()!!.meals)
                    }
                }

                override fun onFailure(p0: Call<RandomMealResponse?>, p1: Throwable) {
                    Log.e("Home", "Failed to fetch Search Category", p1)
                }
            })
    }

    fun observeSearchCategoryLiveData(): LiveData<List<Meal>> {
        return SearchCategoryLiveData
    }
}