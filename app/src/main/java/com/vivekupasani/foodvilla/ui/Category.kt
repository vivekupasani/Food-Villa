package com.vivekupasani.foodvilla.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.vivekupasani.foodvilla.adapters.CategoryHomeAdapter
import com.vivekupasani.foodvilla.databinding.FragmentCategoryBinding
import com.vivekupasani.foodvilla.models.Category
import com.vivekupasani.foodvilla.ui.activity.categories
import com.vivekupasani.foodvilla.viewmodels.HomeViewModel


class Category : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    private val viewModel: HomeViewModel by viewModels()
    lateinit var categoryHomeAdapter: CategoryHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        viewModel.getCategory()
        observeCategoryList()
        onCategoryItemClick()

    }

    private fun onCategoryItemClick() {
        categoryHomeAdapter.onItemClick = { item ->
            val intent = Intent(activity, categories::class.java)
            intent.putExtra(Home.MEAL_CATEGORY, item.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategoryList() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, { List ->
            categoryHomeAdapter.setCategoryList(List as ArrayList<Category>)
        })
    }

    private fun setUpRecyclerView() {
        categoryHomeAdapter = CategoryHomeAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryHomeAdapter
        }
    }
}