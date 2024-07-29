package com.vivekupasani.foodvilla.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vivekupasani.foodvilla.R
import com.vivekupasani.foodvilla.adapters.CategoryHomeAdapter
import com.vivekupasani.foodvilla.adapters.MostPopularMealAdapter
import com.vivekupasani.foodvilla.databinding.FragmentHomeBinding
import com.vivekupasani.foodvilla.models.Category
import com.vivekupasani.foodvilla.models.CategoryMeals
import com.vivekupasani.foodvilla.models.Meal
import com.vivekupasani.foodvilla.ui.activity.MealActivity
import com.vivekupasani.foodvilla.ui.activity.categories
import com.vivekupasani.foodvilla.viewmodels.HomeViewModel


class Home : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    lateinit var popularAdapter: MostPopularMealAdapter
    lateinit var categoryHomeAdapter: CategoryHomeAdapter
    private lateinit var randomMeal: Meal

    companion object {
        const val MEAL_ID = "com.vivekupasani.foodvilla.ui.mealid"
        const val MEAL_NAME = "com.vivekupasani.foodvilla.ui.mealname"
        const val MEAL_THUMB = "com.vivekupasani.foodvilla.ui.mealthumb"
        const val MEAL_CATEGORY = "com.vivekupasani.foodvilla.ui.mealCategory"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        onSearchButtonClick()

        //Random Meal
        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        //Popular Item
        setPopularItemRecyclerView()
        viewModel.getPopularItems()
        observePopularMeal()
        onPopularMealClick()

        //Category List
        setCategoryItemRecyclerView()
        viewModel.getCategory()
        observeCategoryList()
        onCategoryItemClick()

    }

    private fun onSearchButtonClick() {
        binding.imgSearch.setOnClickListener {
            navController.navigate(R.id.action_home2_to_search)
        }
    }

    private fun onCategoryItemClick() {
        categoryHomeAdapter.onItemClick = { item ->
            val intent = Intent(activity, categories::class.java)
            intent.putExtra(MEAL_CATEGORY, item.strCategory)
            startActivity(intent)
        }
    }

    private fun onPopularMealClick() {
        popularAdapter.onItemClick = { Meal ->

            val intent = Intent(activity, MealActivity::class.java)
            intent.apply {
                putExtra(MEAL_ID, Meal.idMeal)
                putExtra(MEAL_NAME, Meal.strMeal)
                putExtra(MEAL_THUMB, Meal.strMealThumb)
            }
            startActivity(intent)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(context, MealActivity::class.java)
            intent.apply {
                putExtra(MEAL_NAME, randomMeal.strMeal)
                putExtra(MEAL_THUMB, randomMeal.strMealThumb)
                putExtra(MEAL_ID, randomMeal.idMeal)
            }
            startActivity(intent)
        }
    }

    private fun setCategoryItemRecyclerView() {
        categoryHomeAdapter = CategoryHomeAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryHomeAdapter
        }
    }

    private fun observeCategoryList() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, { List ->
            categoryHomeAdapter.setCategoryList(List as ArrayList<Category>)
        })
    }

    private fun setPopularItemRecyclerView() {
        popularAdapter = MostPopularMealAdapter()
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter

        }
    }

    private fun observePopularMeal() {
        val recyclerView = viewModel.observePopularItems().observe(viewLifecycleOwner, { List ->
            popularAdapter.setMeal(mealList = List as ArrayList<CategoryMeals>)
        })
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMeal().observe(viewLifecycleOwner, { meal ->
            Glide.with(this@Home).load(meal.strMealThumb).into(binding.imgRandomMeal)
            this.randomMeal = meal
        })
    }
}