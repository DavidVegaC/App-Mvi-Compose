package com.davega.auth.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.davega.auth.data.cipher.CipherDecryption
import com.davega.auth.data.cipher.CipherEncryption
import com.davega.auth.data.cipher.decryptData
import com.davega.auth.data.cipher.encryptData
import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.auth.R
import javax.crypto.Cipher
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun createPrompt(
    activity: FragmentActivity,
    cipher: Cipher,
    onSuccess: Cipher.() -> Unit,
    onFailed: () -> Unit,
    onError: (errorCode: Int, errString: CharSequence) -> Unit
){
    val executor = ContextCompat.getMainExecutor(activity.baseContext)
    val callback = object : BiometricPrompt.AuthenticationCallback(){

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            onError(errorCode, errString)
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            onFailed()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            result.cryptoObject?.cipher?.onSuccess()
        }

    }
    val prompt = BiometricPrompt(
        activity,
        executor,
        callback
    )
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(activity.getString(R.string.login))
        .setSubtitle(activity.getString(R.string.biometric_login_msg))
        .setConfirmationRequired(false)
        .setNegativeButtonText(activity.getString(R.string.cancel))
        .build()
    prompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
}

object LocalBiometricActions {

    val current @Composable get(): BiometricActions? {
        val activity = LocalContext.current.getActivity() ?: return null
        if(BiometricManager.from(activity.applicationContext).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) != BiometricManager.BIOMETRIC_SUCCESS){
            return null
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return null
        }
        return remember {
            BiometricActionsImpl(
                activity = activity
            )
        }
    }

    private fun Context.getActivity(): FragmentActivity? = when (this) {
        is FragmentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

}

interface BiometricActions {

    suspend fun encryption(key: String, value: String): BiometricResult

    suspend fun decryption(key: String, value: CiphertextWrapper): BiometricResult

}

private class BiometricActionsImpl(
    private val activity: FragmentActivity
): BiometricActions {

    @SuppressLint("NewApi")
    override suspend fun encryption(key: String, value: String): BiometricResult = suspendCoroutine {
        val cipher = CipherEncryption(
            keyName = key
        )
        createPrompt(
            activity = activity,
            cipher = cipher,
            onSuccess = {
                it.resume(
                    BiometricResult.SuccessEncryption(
                        cipherValue = encryptData(value)
                    )
                )
            },
            onFailed = {

            },
            onError = { errorCode, errString ->
                if(errorCode == BiometricPrompt.ERROR_USER_CANCELED || errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON){
                    it.resume(
                        BiometricResult.Cancel
                    )
                } else {
                    it.resume(
                        BiometricResult.Error(
                            errorCode = errorCode,
                            description = errString.toString()
                        )
                    )
                }
            }
        )
    }

    @SuppressLint("NewApi")
    override suspend fun decryption(key: String, value: CiphertextWrapper): BiometricResult = suspendCoroutine {
        val cipher = CipherDecryption(
            key,
            initializationVector = value.initializationVector
        )
        createPrompt(
            activity = activity,
            cipher = cipher,
            onSuccess = {
                it.resume(
                    BiometricResult.SuccessDecryption(
                        value = decryptData(value.ciphertext)
                    )
                )
            },
            onFailed = {

            },
            onError = { errorCode, errString ->
                if(errorCode == BiometricPrompt.ERROR_USER_CANCELED){
                    it.resume(
                        BiometricResult.Cancel
                    )
                } else {
                    it.resume(
                        BiometricResult.Error(
                            errorCode = errorCode,
                            description = errString.toString()
                        )
                    )
                }
            }
        )
    }

}

sealed class BiometricResult {
    class SuccessEncryption(
        val cipherValue: CiphertextWrapper,
    ): BiometricResult()
    class SuccessDecryption(
        val value: String
    ): BiometricResult()
    class Error(
        val errorCode: Int,
        val description: String
    ): BiometricResult()
    object Cancel: BiometricResult()
}