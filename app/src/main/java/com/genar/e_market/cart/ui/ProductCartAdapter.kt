package com.genar.e_market.cart.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genar.e_market.databinding.ItemProductCartBinding
import com.genar.e_market.productList.model.ProductUIModel

class ProductCartAdapter(
    private var products: List<ProductUIModel>,
) : RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder>() {

    private var addClickListener: ((ProductUIModel) -> Unit)? = null
    private var deleteClickListener: ((ProductUIModel) -> Unit)? = null

    fun setOnAddClickListener(listener: (ProductUIModel) -> Unit) {
        addClickListener = listener
    }

    fun setOnDeleteClickListener(listener: (ProductUIModel) -> Unit) {
        deleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCartViewHolder {
        return ProductCartViewHolder(
            ItemProductCartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductCartViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun refreshList(newList: List<ProductUIModel>) {
        products = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = products.size

    inner class ProductCartViewHolder(
        private val binding: ItemProductCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUIModel) {
            binding.apply {

                tvPriceValue.text = product.price.toString()
                tvProductLabel.text = product.name

                tvCount.text = product.count.toString()
                btnRemove.setOnClickListener {
                    deleteClickListener?.invoke(product)
                }
                btnAdd.setOnClickListener {
                    addClickListener?.invoke(product)
                }
            }
        }
    }

}