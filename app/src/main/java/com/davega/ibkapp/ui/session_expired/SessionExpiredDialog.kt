package com.davega.ibkapp.ui.session_expired

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davega.ibkapp.R
import com.davega.ui.base.ScreenDialog
import com.davega.ui.components.AppButton

class SessionExpiredDialog: ScreenDialog() {

    @Composable
    override fun Screen(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.session_expired),
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(id = R.string.session_expired_msg),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 24.dp,
                        bottom = 24.dp
                    )
            )
            AppButton(
                text = stringResource(id = R.string.str_ok),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                dismiss()
            }
        }
    }

}