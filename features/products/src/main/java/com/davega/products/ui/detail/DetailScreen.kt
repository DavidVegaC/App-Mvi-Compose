package com.davega.products.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davega.products.R
import com.davega.products.domain.product.entities.Product
import com.davega.products.ui.detail.mapper.UiMapper
import com.davega.products.ui.detail.mapper.UiMapperDetail
import com.davega.ui.components.AppLoading
import com.davega.ui.components.AppRetry

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
) = with(viewModel){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                uiState.product.UiMapperDetail()
            }
        }
        item {
            Text(
                text = stringResource(id = R.string.movements),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
        if(uiState.movements.isLoading){
            item {
                AppLoading(
                    label = stringResource(id = R.string.loading_movements),
                    modifier = Modifier.padding(120.dp)
                )
            }
            return@LazyColumn
        }
        if(uiState.movements.isError){
            item {
                AppRetry(
                    modifier = Modifier.padding(top = 120.dp)
                ) {
                    loadMovements()
                }
            }
        }
        items(uiState.movements.value.size){
            val movement = uiState.movements.value[it]
            movement.UiMapper()
        }
    }
}