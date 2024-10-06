package com.vivekupasani.foodvilla.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vivekupasani.foodvilla.adapters.favoritesAdapter
import com.vivekupasani.foodvilla.databinding.FragmentFavoriteBinding
import com.vivekupasani.foodvilla.models.Meal
import com.vivekupasani.foodvilla.ui.activity.MealActivity
import com.vivekupasani.foodvilla.viewmodels.DatabaseViewModel

class Favorite : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: DatabaseViewModel by viewModels()
    private lateinit var favoritesAdapter: favoritesAdapter
    private lateinit var mealList: ArrayList<Meal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        fetchDataFromDatabase()
        onItemClick()
        onDeleteBtnClick()
    }

    private fun onDeleteBtnClick() {
        favoritesAdapter.onDeleteClick = { meal ->
            viewModel.deleteMeal(meal)
        }
    }

    private fun onItemClick() {
        favoritesAdapter.onItemClick = { meal ->
            val intent = Intent(context, MealActivity::class.java)
            intent.apply {
                putExtra(Home.MEAL_NAME, meal.strMeal)
                putExtra(Home.MEAL_THUMB, meal.strMealThumb)
                putExtra(Home.MEAL_ID, meal.idMeal)
            }
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesAdapter = favoritesAdapter(requireContext(), arrayListOf())
        binding.favoriteRecyclerView.adapter = favoritesAdapter
    }

    private fun fetchDataFromDatabase() {
        viewModel.getMeal().observe(viewLifecycleOwner) { mealList ->
            this.mealList = mealList as ArrayList<Meal>
            mealList.let {
                favoritesAdapter.setCategoryList(it)
            }
            if (mealList.isNotEmpty()) {
                binding.isEmpty.visibility = View.INVISIBLE
            } else {
                binding.isEmpty.visibility = View.VISIBLE
            }
        }
    }
}
