package com.vivekupasani.foodvilla.retrofit

import com.vivekupasani.foodvilla.models.CategoryList
import com.vivekupasani.foodvilla.models.CategoryNames
import com.vivekupasani.foodvilla.models.RandomMealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {


    @GET("random.php")
    fun getRandomMeal(): Call<RandomMealResponse>

    @GET("lookup.php")
    fun getDetailedMeal(@Query("i") id: String): Call<RandomMealResponse>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName: String): Call<CategoryList>

    @GET("categories.php")
    fun getCategory(): Call<CategoryNames>

    @GET("filter.php")
    fun getCategories(@Query("c") categoryName: String): Call<CategoryList>

    @GET("search.php")
    fun searchCategory(@Query("s") searchQuery: String): Call<RandomMealResponse>
}