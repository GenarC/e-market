package com.genar.e_market.cart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.genar.e_market.databinding.FragmentCartBinding
import com.genar.e_market.extensions.observeFlow
import com.genar.e_market.productList.model.ProductUIModel
import com.genar.e_market.productList.ui.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by activityViewModels()

    private lateinit var productCartAdapter: ProductCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getProductsFromCart()

        viewLifecycleOwner.observeFlow(
            viewModel.cartProductList,
            ::handleCartProductList,
            Lifecycle.State.STARTED
        )
    }

    private fun initViews() {
        productCartAdapter = ProductCartAdapter(emptyList())
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCart.adapter = productCartAdapter

        productCartAdapter.setOnAddClickListener {
            viewModel.addItem(it)
            refreshData()
        }

        productCartAdapter.setOnDeleteClickListener {
            viewModel.removeItem(it)
            refreshData()
        }
    }

    private fun handleCartProductList(products: List<ProductUIModel>?) {
        productCartAdapter.refreshList(products ?: emptyList())
    }

    private fun refreshData() {
        viewModel.getProductsFromCart()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}