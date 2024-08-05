package com.genar.e_market.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.genar.e_market.databinding.FragmentHomeBinding
import com.genar.e_market.extensions.observeFlow
import com.genar.e_market.home.model.ProductUIModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var productAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getProductList()

        viewLifecycleOwner.observeFlow(
            viewModel.productList,
            ::handleProductList,
            Lifecycle.State.STARTED
        )
    }

    private fun initViews() {
        productAdapter = ProductListAdapter(emptyList())
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = productAdapter

        binding.containerSearch.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                productAdapter.searchProduct(text.toString())
            }
        )

        binding.rvProducts.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                    productAdapter.loadNextData(viewModel.loadNextData())
                }
            }
        })
    }

    private fun handleProductList(productList: List<ProductUIModel>?) {
        productAdapter.refreshList(productList ?: emptyList())
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}