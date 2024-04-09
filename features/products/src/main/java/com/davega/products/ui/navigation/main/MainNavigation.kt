package com.davega.products.ui.navigation.main

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.davega.data.shared.utils.JSON
import com.davega.products.R
import com.davega.products.domain.product.entities.Product
import com.davega.products.ui.detail.DetailScreen
import com.davega.products.ui.operations.OperationsScreen
import com.davega.products.ui.products.ProductsScreen

sealed class MainRoutes(
    val label: Int,
    val icon: Int? = null,
    val baseRoute: String,
) {

    abstract val absoluteRoute: String

    object Products: MainRoutes(
        label = R.string.products,
        icon = R.drawable.ic_products,
        baseRoute = "products"
    ){
        override val absoluteRoute: String
            get() = baseRoute
    }
    object Operations: MainRoutes(
        label = R.string.operations,
        icon = R.drawable.ic_operations,
        baseRoute = "operations"
    ){
        override val absoluteRoute: String
            get() = baseRoute
    }
    object Detail: MainRoutes(
        label = R.string.detail,
        baseRoute = "detail"
    ) {
        override val absoluteRoute: String
            get() = "baseRoute?product={$argumentProduct}"

        const val argumentProduct = "product"

        fun getProduct(arguments: Bundle?): Product {
            return arguments?.getString(argumentProduct, "")?.let {
                JSON.parse(it)
            }!!
        }

        fun createRoute(product: Product): String {
            return absoluteRoute.replace("{$argumentProduct}", JSON.stringify(product))
        }
    }

    companion object {
        fun values() = listOf(Products, Operations, Detail)
    }

}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = MainRoutes.Products.absoluteRoute,
        modifier = modifier
    ) {
        composable(MainRoutes.Products.absoluteRoute){
            ProductsScreen(
                navController = navHostController
            )
        }
        composable(
            route = MainRoutes.Operations.absoluteRoute
        ){
            OperationsScreen()
        }
        composable(
            route = MainRoutes.Detail.absoluteRoute,
            arguments = listOf(
                navArgument(MainRoutes.Detail.argumentProduct){
                    type = NavType.StringType
                }
            )
        ){
            DetailScreen(
                product = MainRoutes.Detail.getProduct(it.arguments)
            )
        }
    }
}

@Composable
fun NavHostController.currentMainRouteAsState(): State<MainRoutes?> {
    val currentRoute = remember {
        mutableStateOf<MainRoutes?>(null)
    }
    val navBackStackEntry by currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val result = MainRoutes.values().find { route -> currentDestination?.hierarchy?.any { it.route == route.absoluteRoute } == true }
    LaunchedEffect(result){
        currentRoute.value = result
    }
    return currentRoute
}

data class BottomNavigationItem(
    val label: String = "",
    val icon: Painter? = null,
    val baseRoute: String = "",
    val absoluteRoute: String = "",
) {

    @Composable
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        val items = mutableListOf<BottomNavigationItem>()
        MainRoutes.values().forEach { mainRoute ->
            if (mainRoute == MainRoutes.Detail) {
                return@forEach
            }

            items.add(
                BottomNavigationItem(
                    label = stringResource(id = mainRoute.label),
                    icon = mainRoute.icon?.let { painterResource(id = it) },
                    baseRoute = mainRoute.baseRoute,
                    absoluteRoute = mainRoute.absoluteRoute,
                )
            )
        }
        return items
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    //initializing the default selected item
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */
    val navHostController = rememberNavController()
    val currentRoute by navHostController.currentMainRouteAsState()

    //scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon!!,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            if(currentRoute is MainRoutes.Detail){
                                navHostController.popBackStack()
                                if(navigationItem.absoluteRoute == "products"){
                                    return@NavigationBarItem
                                }
                            }
                            navHostController.navigate(navigationItem.absoluteRoute){
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        //We need to setup our NavHost in here
        MainNavigation(
            navHostController = navHostController,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}