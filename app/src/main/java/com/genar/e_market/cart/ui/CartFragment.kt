package com.genar.e_market.cart.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.genar.e_market.databinding.FragmentCartBinding
import com.genar.e_market.extensions.observeFlow
import com.genar.e_market.main.ui.OnItemClickListener
import com.genar.e_market.productList.model.ProductUIModel
import com.genar.e_market.productList.ui.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by activityViewModels()

    private lateinit var productCartAdapter: ProductCartAdapter


    private lateinit var productClickListener: OnItemClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemClickListener) {
            productClickListener = context
        }
    }

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
        }

        productCartAdapter.setOnDeleteClickListener {
            viewModel.removeItem(it)
        }

        productCartAdapter.setOnItemClickedListener {
            productClickListener.onItemClick(it)
        }
    }

    private fun handleCartProductList(products: List<ProductUIModel>?) {
        if(products.isNullOrEmpty()){
            binding.rvCart.isVisible = false
            binding.containerEmptyCart.isVisible = true

            binding.tvPriceValue.text = "0.0"
        }else{
            binding.rvCart.isVisible = true
            binding.containerEmptyCart.isVisible = false

            productCartAdapter.refreshList(products)
            binding.tvPriceValue.text = products.sumOf { it.price * it.count }.toString()
        }
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