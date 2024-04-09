package com.davega.products.ui.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.davega.products.R
import com.davega.products.ui.navigation.main.MainRoutes
import com.davega.products.ui.products.mappers.UiMapper
import com.davega.ui.components.AppLoading
import com.davega.ui.components.AppRetry
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductsScreen(
    navController: NavController,
    viewModel: ProductsViewModel = getViewModel()
) = with(viewModel) {
    if(uiState.products.isLoading && uiState.products.value.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AppLoading(
                label = stringResource(id = R.string.loading_products)
            )
        }
        return@with
    }
    if(uiState.products.isError){
        AppRetry(
            modifier = Modifier.fillMaxSize()
        ) {
            loadProducts()
        }
        return@with
    }
    val swipeRefreshState = rememberSwipeRefreshState(
        uiState.products.isLoading
    )
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            refreshProducts()
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.products.value.size){
                val product = uiState.products.value[it]
                product.UiMapper(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp
                    )
                ) {
                    val route = MainRoutes.Detail.createRoute(
                        product = product
                    )
                    navController.navigate(route)
                }
            }
        }
    }
}