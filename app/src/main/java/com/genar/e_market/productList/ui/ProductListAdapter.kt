package com.genar.e_market.productList.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.genar.e_market.databinding.ItemHomeProductBinding
import com.genar.e_market.productList.model.ProductUIModel

class ProductListAdapter(
    private var productList: List<ProductUIModel>
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private var filteredProducts: List<ProductUIModel> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            ItemHomeProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(filteredProducts[position])
    }

    override fun getItemCount(): Int = filteredProducts.size


    fun refreshList(newList: List<ProductUIModel>) {
        productList = newList
        filteredProducts = productList
        notifyDataSetChanged()
    }

    fun searchProduct(query: String) {
        filteredProducts = if (query.isEmpty()) {
            productList
        } else {
            productList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    fun loadNextData(newData: List<ProductUIModel>) {
        productList = productList + newData
        filteredProducts = productList
        notifyDataSetChanged()
    }

    class ProductViewHolder(var binding: ItemHomeProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productUIModel: ProductUIModel) {
            binding.tvProductPrice.text = productUIModel.price
            binding.tvProductTitle.text = productUIModel.name
            binding.ivProduct.load(productUIModel.image) {
                crossfade(true)
                scale(Scale.FIT)
                diskCachePolicy(CachePolicy.ENABLED)
                transformations(RoundedCornersTransformation(4f))
            }
        }
    }
}