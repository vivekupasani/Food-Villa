package com.vivekupasani.foodvilla.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.vivekupasani.foodvilla.adapters.SearchAdapter
import com.vivekupasani.foodvilla.databinding.FragmentSearchBinding
import com.vivekupasani.foodvilla.models.Meal
import com.vivekupasani.foodvilla.ui.activity.MealActivity
import com.vivekupasani.foodvilla.viewmodels.SearchViewModel

class Search : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private var categorySize: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.itemNotFound.visibility = View.INVISIBLE
        setUpSearchRecyclerView()
        setUpSearchButton()
        onsearchedItemClick()
    }

    private fun onsearchedItemClick() {
        searchAdapter.onItemClick = {
            val intent = Intent(requireContext(), MealActivity::class.java)
            intent.apply {
                putExtra(Home.MEAL_NAME, it.strMeal)
                putExtra(Home.MEAL_ID, it.idMeal)
                putExtra(Home.MEAL_THUMB, it.strMealThumb)
            }
            startActivity(intent)
        }
    }

    private fun setUpSearchButton() {
        binding.icSearch.setOnClickListener {
            val searchQuery = binding.edSearch.text.toString()
            if (searchQuery.isNotEmpty()) {
                viewModel.searchCategory(searchQuery)
                observeLiveData(searchQuery)
                binding.edSearch.text.clear()
            } else {
                Toast.makeText(requireContext(), "Enter Meal Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeLiveData(searchQuery: String) {
        viewModel.observeSearchCategoryLiveData().observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.itemFound.visibility = View.INVISIBLE
                binding.rvSearch.visibility = View.INVISIBLE
                binding.itemNotFound.visibility = View.VISIBLE
                binding.itemNotFound.text = "No items found for \"$searchQuery\""
            } else {
                searchAdapter.setCategoryMeal(list as ArrayList<Meal>)
                categorySize = list.size.toString()
                binding.itemFound.text = "Found $categorySize items in $searchQuery"

                binding.itemFound.visibility = View.VISIBLE
                binding.rvSearch.visibility = View.VISIBLE
                binding.itemNotFound.visibility = View.INVISIBLE
            }
        }
    }

    private fun setUpSearchRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }
}
