package xyz.norlib.bank305.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import xyz.norlib.bank305.R
import xyz.norlib.bank305.data.User
import xyz.norlib.bank305.integration.BsaAuthentication
import xyz.norlib.bank305.integration.BsaDeviceRegistrationRepository
import xyz.norlib.bank305.ui.components.ErrorMessage
import xyz.norlib.bank305.ui.components.IntroductionMessage
import xyz.norlib.bank305.ui.components.Loader

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: (User) -> Unit,
    onCancleButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IntroductionMessage(
            title = R.string.welcome_back,
            message = R.string.enter_user_key,
            modifier = modifier
        )
        LoginForm(
            onLoginButtonClicked = onLoginButtonClicked,
            onCancleButtonClicked = onCancleButtonClicked
        )
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: (User) -> Unit,
    onCancleButtonClicked: () -> Unit
) {
    var userKey by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var hasErrorDuplicate by remember { mutableStateOf(false) }

    var context = LocalContext.current



    Column(
        modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = userKey,
            onValueChange = { userKey = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(stringResource(id = R.string.user_key)) }
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(stringResource(id = R.string.name)) }
        )

        Spacer(modifier = Modifier.size(24.dp))

        if (loading) {
            Loader()
        } else {
            Button(onClick = {
                loading = true
                BsaDeviceRegistrationRepository.userCheckAndOtpDispatch(
                    userKey = userKey,
                    name = name,
                    onSuccess = {
                        onLoginButtonClicked(
                            User(
                                userKey = userKey,
                                name = name,
                                email = userKey,
                                phone = phone
                            )
                        )
                    },
                    onFailed = {
                        loading = false
                        hasErrorDuplicate = true
                    },
                )
            }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.signin_btn))
            }
            Spacer(modifier = Modifier.size(20.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loading = true
                    BsaAuthentication.inAppAuthentication(
                        userKey,
                        true,
                        context as FragmentActivity,
                        onSuccess = {},
                        onFailed = {
                            loading = false
                        },
                        onProcess = {})
                }) {
                Text(
                    "If error try In app Authentication",
                )
            }
        }

        if (hasErrorDuplicate) {
            ErrorMessage(message = "An arror occur when try to send otp")
        }

        Spacer(modifier = Modifier.size(24.dp))

        TextButton(onClick = { onCancleButtonClicked() }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.no_account))
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLoginButtonClicked = {},
        onCancleButtonClicked = {})
}