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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.norlib.bank305.R
import xyz.norlib.bank305.data.User
import xyz.norlib.bank305.integration.BsaRegistrationRepository
import xyz.norlib.bank305.ui.components.ErrorMessage
import xyz.norlib.bank305.ui.components.IntroductionMessage
import xyz.norlib.bank305.ui.components.Loader

@Composable
fun OTPScreen(
    modifier: Modifier = Modifier,
    onSendButtonClicked: (String?) -> Unit,
    onResendButtonClicked: () -> Unit,
    user: User
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IntroductionMessage(
            title = R.string.otp_title,
            message = R.string.otp_message,
            titleData = user.name,
            modifier = modifier
        )
        OTPForm(
            onSendButtonClicked = onSendButtonClicked,
            onResendButtonClicked = onResendButtonClicked,
            user = user
        )
    }
}

@Composable
fun OTPForm(
    user: User,
    modifier: Modifier = Modifier,
    onSendButtonClicked: (String?) -> Unit,
    onResendButtonClicked: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }

    Column(
        modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = otp,
            onValueChange = { otp = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(stringResource(id = R.string.otp_field)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.size(24.dp))

        if (loading) {
            Loader()
        } else {
            Button(onClick = {
                loading = true
                BsaRegistrationRepository.emailVerificationConfirmation(email = user.email, authNumber = otp,
                    onSuccess = {
                        Log.d("BSA_SUCCESS", it?.data?.disposeToken.toString())
                        onSendButtonClicked(it?.data?.disposeToken)
                    },
                    onFailed = {
                        Log.d("BSA_ERROR", it?.errorMessage.toString())
                        Log.d("BSA_ERROR", it?.errorCode.toString())
                        hasError = true
                        loading = false
                    })
            }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.send_btn))
            }
        }

        if (hasError) {
            ErrorMessage(message = "This auth number is invalid, please try again")
        }

        Spacer(modifier = Modifier.size(24.dp))

        TextButton(onClick = { onResendButtonClicked() }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.resend_btn))
        }
    }
}
