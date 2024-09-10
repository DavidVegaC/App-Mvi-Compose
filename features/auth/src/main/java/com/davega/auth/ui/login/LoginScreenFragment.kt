package com.davega.auth.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.davega.auth.R
import com.davega.auth.di.loadModules
import com.davega.auth.ui.login.dialogs.ConfigBiometricError
import com.davega.auth.ui.login.interactors.LoginUiEvent
import com.davega.auth.ui.login.interactors.LoginUiIntent
import com.davega.auth.ui.login.interactors.LoginUiState
import com.davega.auth.ui.utils.LocalBiometricActions
import com.davega.ui.components.AppAnimation
import com.davega.ui.components.AppButton
import com.davega.ui.components.AppTextField
import com.davega.ui.compose.fragment.AppComposeFragment
import com.davega.ui.compose.stateful.OnUiEvent
import com.davega.ui.compose.stateful.Stateful
import com.davega.ui.compose.stateful.execUiIntent
import com.davega.ui.compose.stateful.uiState
import com.davega.ui.compose.util.ScreenPreview
import com.davega.ui.utils.navigate

class LoginScreenFragment : AppComposeFragment() {

    override fun onInit() {
        loadModules()
    }

    @Composable
    override fun Screen() = Stateful<LoginBaseViewModel> {
        val uiState by uiState()
        val biometricActions = LocalBiometricActions.current
        OnUiEvent {
            when(it) {
                is LoginUiEvent.GoToMain -> {
                    navigate(LoginScreenFragmentDirections.loginScreenToMainScreenAction())
                }

                is LoginUiEvent.ShowConfigBiometricError -> {
                    ConfigBiometricError(
                        onAction = {
                            navigate(LoginScreenFragmentDirections.loginScreenToMainScreenAction())
                        }
                    ).show()
                }
                is LoginUiEvent.ShowBiometricError -> {
                    ConfigBiometricError(
                        onAction = {
                            navigate(LoginScreenFragmentDirections.loginScreenToMainScreenAction())
                        }
                    ).show()
                }
                is LoginUiEvent.EncryptPin -> {
                    execUiIntent(LoginUiIntent.EncryptPin(
                        biometricActions = biometricActions,
                        key = it.key,
                        value = it.value,
                    ))
                }
                is LoginUiEvent.DecryptPin -> {
                    biometricActions?.let { biometricActions ->
                        execUiIntent(LoginUiIntent.Decryption(
                            biometricActions = biometricActions,
                            key = it.key,
                            value = it.value,
                        ))
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(150.dp)
                ){
                    AppAnimation(
                        resId = R.raw.login,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = buildAnnotatedString {
                        append(
                            stringResource(id = com.davega.ibkapp.R.string.app_name)
                        )
                        addStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.background
                            ),
                            start = 0,
                            end = 4
                        )
                        addStyle(
                            style = SpanStyle(
                                color = Color(0xFF3385FF)
                            ),
                            start = 4,
                            end = 8
                        )
                    },
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            ConstraintLayout(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    )
                    .padding(16.dp)
            ) {
                val (contentRef, errorRef, buttonRef) = createRefs()
                Column(
                    modifier = Modifier
                        .constrainAs(contentRef){
                            width = Dimension.fillToConstraints
                            top.linkTo(parent.top)
                            bottom.linkTo(buttonRef.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    val pinFocusRequester = remember {
                        FocusRequester()
                    }
                    val focusManager = LocalFocusManager.current
                    AppTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = uiState.dni,
                        label = stringResource(id = R.string.dni),
                        placeholder = stringResource(id = R.string.dni_ej),
                        onChange = {
                            execUiIntent(LoginUiIntent.SetDni(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        isError = uiState.isError,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                pinFocusRequester.requestFocus()
                            }
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        AppTextField(
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(pinFocusRequester),
                            value = uiState.password,
                            label = stringResource(id = R.string.password),
                            placeholder = stringResource(id = R.string.password_ej),
                            onChange = {
                                execUiIntent(LoginUiIntent.SetPassword(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true,
                            isError = uiState.isError,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.clearFocus()
                                }
                            ),
                            visualTransformation = if(uiState.isVisible){
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        execUiIntent(LoginUiIntent.HandlerToggleVisibility)
                                    }
                                ) {
                                    Icon(
                                        painter = if(uiState.isVisible){
                                            painterResource(id = R.drawable.ic_baseline_visibility_off_24)
                                        } else {
                                            painterResource(id = R.drawable.ic_baseline_visibility_24)
                                        },
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                        biometricActions?.let {
                            Box(
                                modifier = Modifier
                                    .padding(
                                        start = 12.dp
                                    )
                                    .background(
                                        color = if(uiState.cipherValue != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(
                                        12.dp
                                    )
                                    .clickable(
                                        onClick = {
                                            uiState.cipherValue?.let {
                                                execUiIntent(LoginUiIntent.DecryptPin(
                                                    key = uiState.dni,
                                                    value = it
                                                ))
                                            }
                                        },
                                        enabled = uiState.cipherValue != null
                                    )
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_fingerprint),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = Color.White)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = uiState.remember,
                            onCheckedChange = {
                                execUiIntent(LoginUiIntent.HandleRemember)
                            }
                        )
                        Text(
                            text = stringResource(id = R.string.remember),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if(uiState.isError){
                    Text(
                        text = stringResource(id = R.string.login_error),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.constrainAs(errorRef){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(buttonRef.top, margin = 12.dp)
                        }
                    )
                }
                AppButton(
                    modifier = Modifier
                        .constrainAs(buttonRef){
                            width = Dimension.fillToConstraints
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    text = stringResource(id = R.string.login),
                    enabled = uiState.isValid,
                ) {
                    execUiIntent(LoginUiIntent.Login(
                        dni = uiState.dni,
                        password = uiState.password,
                        remember = uiState.remember
                    ))
                }
            }
        }
    }

    @Composable
    override fun DefaultPreview() {
        ScreenPreview(
            uiState = LoginUiState(),
        ) {
            Screen()
        }
    }
}