package com.davega.products.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.davega.products.ui.navigation.main.Navigation
import com.davega.products.ui.navigation.main.Screen
import com.davega.products.ui.navigation.main.currentRoute
import com.davega.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen: BaseFragment() {

    @Composable
    override fun Screen(){
        Content()
    }

}

@Composable
fun Content() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            when (currentRoute(navController)) {
                Screen.Products.route, Screen.Operations.route-> {
                    BottomNavigationUI(navController)
                }
            }
        },
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Navigation(navController, Modifier.padding(it))
        }
    }
}

@Composable
fun BottomNavigationUI(navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = contentColorFor(backgroundColor),
    ) {
        val items = listOf(
            Screen.ProductsNav,
            Screen.OperationsNav,
        )
        items.forEach { item ->
            BottomNavigationItem(
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
                icon = {
                    Icon(painter = painterResource(id = item.navIcon),
                        contentDescription = null, modifier = Modifier
                            .size(27.dp)
                            .padding(bottom = 2.dp))
                },
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}