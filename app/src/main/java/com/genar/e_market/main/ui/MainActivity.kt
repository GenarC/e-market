package com.genar.e_market.main.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.genar.e_market.R
import com.genar.e_market.databinding.ActivityMainBinding
import com.genar.e_market.productDetail.ui.ProductDetailFragment
import com.genar.e_market.productList.model.ProductUIModel
import com.genar.e_market.productList.ui.BottomNavigationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val bottomNavigationAdapter = BottomNavigationAdapter(supportFragmentManager, lifecycle)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initiateBottomNavigation()
    }

    private fun initiateBottomNavigation() {
        binding.mainBottomNavigation.inflateMenu(R.menu.bottom_navigation_menu)

        with(binding.mainViewPager) {
            adapter = bottomNavigationAdapter
            isUserInputEnabled = false
        }

        binding.mainBottomNavigation.setOnItemSelectedListener {
            selectMenuItem(it)
            true
        }

        binding.mainBottomNavigation.setOnItemReselectedListener {
            selectMenuItem(it)
        }
    }

    private fun selectMenuItem(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                binding.mainViewPager.setCurrentItem(BottomNavigationAdapter.HOME_PAGE_INDEX, true)
            }

            R.id.nav_cart -> {
                binding.mainViewPager.setCurrentItem(BottomNavigationAdapter.CART_PAGE_INDEX, true)
            }

            R.id.nav_favourite -> {
//                binding.mainViewPager.setCurrentItem(BottomNavigationAdapter.FAVOURITE_PAGE_INDEX, false)
            }

            R.id.nav_person -> {
//                binding.mainViewPager.setCurrentItem(BottomNavigationAdapter.PERSON_PAGE_INDEX, false)
            }
        }
    }

    override fun onItemClick(product: ProductUIModel) {
        supportFragmentManager.beginTransaction()
            .replace(
                binding.mainFragmentContainer.id,
                ProductDetailFragment.newInstance(product)
            )
            .addToBackStack(null)
            .commit()

        binding.mainFragmentContainer.visibility = VISIBLE
    }

    fun hideFragment() {
        binding.mainFragmentContainer.visibility = View.GONE
    }
}

