package com.genar.e_market.productDetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.request.CachePolicy
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.genar.e_market.R
import com.genar.e_market.databinding.FragmentProductDetailBinding
import com.genar.e_market.main.ui.MainActivity
import com.genar.e_market.productList.model.ProductUIModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_PRODUCT = "product"

@AndroidEntryPoint
class ProductDetailFragment @Inject constructor(
) : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel: ProductDetailViewModel by activityViewModels()
    private val activity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    private var product: ProductUIModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getParcelable(ARG_PRODUCT, ProductUIModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = product?.name
        initViews()
    }

    private fun initViews() {
        product?.let {
            binding.tvProductName.text = it.name
            binding.tvPriceValue.text = it.price
            binding.tvProductDescription.text = it.description
            binding.ivProductImage.load(it.image) {
                crossfade(true)
                scale(Scale.FIT)
                diskCachePolicy(CachePolicy.DISABLED)
                transformations(RoundedCornersTransformation(4f))
            }
        }

        binding.btnAddToCart.setOnClickListener {
            product?.let {
                //addToCartUseCase(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.title = getString(R.string.app_name)
        activity.hideFragment()
    }

    companion object {
        @JvmStatic
        fun newInstance(product: ProductUIModel) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PRODUCT, product)
                }
            }
    }
}