package com.davega.products.ui.navigation.main

import android.util.Base64
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import com.davega.data.shared.utils.JSON
import com.davega.products.R
import com.davega.products.domain.product.entities.Product

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.products,
    @DrawableRes val navIcon: Int = R.drawable.ic_products,
    val objectName: List<String> = emptyList(),
    val objectPath: String = ""
) {

    object Products : Screen("product_screen")
    object Operations : Screen("operations_screen")

    object ProductDetail :
        Screen("recipe_detail_screen", objectName = listOf("product"), objectPath = "/{product}") {

        fun getProduct(savedStateHandle: SavedStateHandle): Product {

            return savedStateHandle.get<String>("product")?.let {
                val raw = Base64.decode(it, Base64.DEFAULT).let(::String)
                JSON.parse(raw)
            }!!
        }

        fun createRoute(product: Product): String {
            val productString: String = JSON.stringify(product)
            val productBase64 = Base64.encodeToString(productString.toByteArray(), Base64.DEFAULT)
            return route.plus("/${productBase64}")
        }
        }

    // Bottom Navigation
    object ProductsNav : Screen("product_screen", title = R.string.products, navIcon = R.drawable.ic_products)

    object OperationsNav : Screen("operations_screen", title = R.string.operations, navIcon = R.drawable.ic_operations)

}