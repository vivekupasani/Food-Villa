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

class MealActivityViewModel(application: Application) : AndroidViewModel(application) {

    //Detailed Meal
    private var detailedMealLiveData = MutableLiveData<Meal>()

    fun getDetailedMeal(id: String) {
        RetrofitInstance.foodApi.getDetailedMeal(id)
            .enqueue(object : Callback<RandomMealResponse?> {
                override fun onResponse(
                    p0: Call<RandomMealResponse?>,
                    p1: Response<RandomMealResponse?>
                ) {
                    if (p1 != null) {
                        detailedMealLiveData.value = p1.body()!!.meals[0]
                    } else {
                        return
                    }
                }

                override fun onFailure(p0: Call<RandomMealResponse?>, p1: Throwable) {
                    Log.e("Home", "Failed to fetch Detailed meal", p1)
                }
            })
    }

    fun observeDetailedMealLiveData(): LiveData<Meal> {
        return detailedMealLiveData
    }
}
