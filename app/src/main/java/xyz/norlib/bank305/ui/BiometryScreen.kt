package xyz.norlib.bank305.ui

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.biometric.BiometricFragment
import androidx.biometric.BiometricManager
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Schema
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.ui.theme.AppTypography
import com.fnsv.bsa.sdk.response.RegisterUserResponse
import xyz.norlib.bank305.R
import xyz.norlib.bank305.data.RegistrationData
import xyz.norlib.bank305.integration.BsaAuthentication
import xyz.norlib.bank305.integration.BsaDeviceRegistrationRepository
import xyz.norlib.bank305.integration.BsaRegistrationRepository
import xyz.norlib.bank305.ui.components.ErrorMessage
import xyz.norlib.bank305.ui.components.IntroductionMessage
import xyz.norlib.bank305.ui.components.Loader

fun Context.findActivity(): FragmentActivity? {
    var currentContext = this
    var previousContext: Context? = null
    while (currentContext is ContextWrapper && previousContext != currentContext) {
        if (currentContext is FragmentActivity) {
            return currentContext
        }
        previousContext = currentContext
        currentContext = currentContext.baseContext
    }
    return null
}

@Composable
fun BiometryScreen(
    modifier: Modifier = Modifier,
    onSendButtonClicked: (String?) -> Unit,
    registrationData: RegistrationData
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IntroductionMessage(
            title = R.string.biometry_title,
            message = R.string.biometry_message,
            modifier = modifier
        )
        BiometryForm(
            onSendButtonClicked = onSendButtonClicked,
            registrationData = registrationData
        )
    }
}

@Composable
fun BiometryForm(
    modifier: Modifier = Modifier,
    onSendButtonClicked: (String?) -> Unit,
    registrationData: RegistrationData
) {
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }

    Column(
        modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //CustomRadioGroup()
        Spacer(modifier = Modifier.size(24.dp))
        if (loading) {
            Loader()
        } else {
            CustomBiometricButton {
                loading = true
                var f: FragmentActivity? = context.findActivity()
                if (f != null) {
                    try {
                        BsaRegistrationRepository.localAuthentifcation(f, onSuccess = {

                            if (registrationData.isLogin) {
                                try {
                                    BsaDeviceRegistrationRepository.deviceReregistration(
                                        registrationData.user,
                                        authType = "3",
                                        disposeToken = registrationData.disposeToken.toString(),
                                        onSuccess = {
                                            BsaAuthentication.inAppAuthentication(
                                                "",
                                                fragmentActivity = f,
                                                onFailed = {},
                                                onSuccess = {
                                                    onSendButtonClicked("")
                                                },
                                                onProcess = {})
                                        },
                                        onFailed = {
                                            loading = false
                                            hasError = true
                                        })
                                } catch (e: Exception) {
                                    onSendButtonClicked("")
                                }
                            } else {
                                BsaRegistrationRepository.register(
                                    registrationData.user,
                                    "3",
                                    registrationData.disposeToken.toString(),
                                    onSuccess = {
                                        //TODO create user account on backend server
                                        BsaAuthentication.inAppAuthentication(
                                            "",
                                            fragmentActivity = f,
                                            onFailed = {},
                                            onSuccess = {
                                                onSendButtonClicked(it?.data?.accessToken)
                                            },
                                            onProcess = {})
                                    },
                                    onFailed = {
                                        loading = false
                                        hasError = true
                                    })
                            }
                        }, onFailed = {
                            loading = false
                            hasError = true
                        })
                    } catch (e: Exception) {
                        onSendButtonClicked("")
                    }
                } else {
                    Log.d("BSA ERROR", "NO ACTIVITY FOUND !")
                }
            }
        }

        if (hasError) {
            ErrorMessage(message = "Failed to register your account")
        }
    }
}

@Composable
fun CustomBiometricButton(onClick: () -> Unit) {
    Row(
        Modifier
            .clickable { onClick() }
            .padding(8.dp)
            .border(
                color = Color.Gray,
                width = 1.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Fingerprint,
            contentDescription = "Selected option",
            modifier = Modifier
                .size(64.dp)
                .padding(16.dp),
            tint = colorResource(id = R.color.bank_green)
        )
        Text(
            text = "Biometric",
            style = AppTypography.titleLarge,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
