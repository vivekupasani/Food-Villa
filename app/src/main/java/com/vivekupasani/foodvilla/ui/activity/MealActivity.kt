package com.vivekupasani.foodvilla.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vivekupasani.foodvilla.R
import com.vivekupasani.foodvilla.databinding.ActivityMealBinding
import com.vivekupasani.foodvilla.models.Meal
import com.vivekupasani.foodvilla.ui.Home
import com.vivekupasani.foodvilla.viewmodels.DatabaseViewModel
import com.vivekupasani.foodvilla.viewmodels.MealActivityViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private val viewModel: MealActivityViewModel by viewModels()
    private val dbViewModel: DatabaseViewModel by viewModels()
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var YoutubeLink: String
    private lateinit var MealList: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getInfoFromIntent()
        setInfoInViews()

        //setting Details from API
        setProgress(true)
        viewModel.getDetailedMeal(mealId)
        observeDetailedMeal()
        onYoutubeClick()
        onFavoriteBtnClick()
    }

    private fun onFavoriteBtnClick() {
        binding.btnFav.setOnClickListener {
            MealList?.let {
                dbViewModel.addMeal(it)
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.goYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(YoutubeLink))
            startActivity(intent)
        }
    }

    private fun observeDetailedMeal() {
        viewModel.observeDetailedMealLiveData().observe(this, { meal ->
            binding.categoryName.text = "Category : ${meal.strCategory}"
            binding.location.text = "Area : ${meal.strArea}"
            binding.detailedInstructions.text = meal.strInstructions

            YoutubeLink = meal.strYoutube!!

            MealList = meal

            setProgress(false)
        })
    }


    private fun setInfoInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetails)
        binding.collapsingToolbar.title = mealName


    }

    private fun getInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(Home.MEAL_ID)!!
        mealThumb = intent.getStringExtra(Home.MEAL_THUMB)!!
        mealName = intent.getStringExtra(Home.MEAL_NAME)!!

        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    fun setProgress(isProgress: Boolean) {
        if (isProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnFav.visibility = View.INVISIBLE
            binding.instruction.visibility = View.INVISIBLE
            binding.detailedInstructions.visibility = View.INVISIBLE
            binding.categoryName.visibility = View.INVISIBLE
            binding.location.visibility = View.INVISIBLE
            binding.goYoutube.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.btnFav.visibility = View.VISIBLE
            binding.instruction.visibility = View.VISIBLE
            binding.detailedInstructions.visibility = View.VISIBLE
            binding.categoryName.visibility = View.VISIBLE
            binding.location.visibility = View.VISIBLE
            binding.goYoutube.visibility = View.VISIBLE
        }
    }
}