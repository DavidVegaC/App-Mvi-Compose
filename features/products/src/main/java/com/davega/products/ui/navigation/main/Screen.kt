package com.davega.products.ui.navigation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.davega.products.R

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
        Screen("product_detail_screen", objectName = listOf("product"), objectPath = "/{product}")

    // Bottom Navigation
    object ProductsNav : Screen("product_screen", title = R.string.products, navIcon = R.drawable.ic_products)

    object OperationsNav : Screen("operations_screen", title = R.string.operations, navIcon = R.drawable.ic_operations)

}