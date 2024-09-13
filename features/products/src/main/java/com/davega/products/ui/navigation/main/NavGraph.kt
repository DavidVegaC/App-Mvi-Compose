package com.davega.products.ui.navigation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.davega.products.ui.detail.DetailScreen
import com.davega.products.ui.operations.OperationsScreen
import com.davega.products.ui.products.ProductsScreen

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier
) {
    NavHost(navController, startDestination = Screen.Products.route, modifier) {
        composable(Screen.Products.route) {
            ProductsScreen(
                navController = navController
            )
        }
        composable(Screen.Operations.route) {
            OperationsScreen()
        }

        composable(
            Screen.ProductDetail.route.plus(Screen.ProductDetail.objectPath),
            arguments = listOf(navArgument(Screen.ProductDetail.objectName[0]) {
                type = NavType.StringType
            })
        ) { DetailScreen() }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBefore("/")
}