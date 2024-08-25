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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.loader.content.Loader
import com.fnsv.bsa.sdk.common.SdkConstant
import xyz.norlib.bank305.R
import xyz.norlib.bank305.data.User
import xyz.norlib.bank305.integration.BsaRegistrationRepository
import xyz.norlib.bank305.ui.components.ErrorMessage
import xyz.norlib.bank305.ui.components.IntroductionMessage
import xyz.norlib.bank305.ui.components.Loader

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onCreateButtonClicked: (user: User) -> Unit,
    onCancleButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IntroductionMessage(
            title = R.string.welcome,
            message = R.string.getting_start,
            modifier = modifier
        )
        RegisterForm(
            onCreateButtonClicked = onCreateButtonClicked,
            onCancleButtonClicked = onCancleButtonClicked
        )
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier, onCreateButtonClicked: (user: User) -> Unit,
    onCancleButtonClicked: () -> Unit
) {
    var userKey by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var hasErrorDuplicate by remember { mutableStateOf(false) }
    var hasErrorOtp by remember { mutableStateOf(false) }

    LazyColumn(
        modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
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
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(stringResource(id = R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.size(24.dp))

            if (!loading) {
                //TODO verify duplication on backend server
                Button(onClick = {
                    loading = true
                    BsaRegistrationRepository.checkDuplicationEmailOrPhone(data = userKey,
                        type = SdkConstant.OtpType.EMAIL,
                        onFailed = {
                            Log.d("BSA", it?.errorCode.toString())
                            Log.d("BSA", it?.httpStatus.toString())
                            Log.d("BSA", it?.errorMessage.toString())
                            BsaRegistrationRepository.sendEmailVerificationOtpCode(userKey,
                                onSuccess = {
                                    onCreateButtonClicked(
                                        User(
                                            userKey = userKey,
                                            name = name,
                                            email = userKey,
                                            phone = phoneNumber
                                        )
                                    )
                                },
                                onFailed = {
                                    Log.d("BSA ERROR", it?.errorMessage.toString())
                                    Log.d("BSA ERROR", it?.errorCode.toString())
                                    loading = false
                                    hasErrorOtp = true
                                })
                        },
                        onSuccess = {
                            BsaRegistrationRepository.sendEmailVerificationOtpCode(userKey,
                                onSuccess = {
                                    onCreateButtonClicked(
                                        User(
                                            userKey = userKey,
                                            name = name,
                                            email = userKey,
                                            phone = phoneNumber
                                        )
                                    )
                                },
                                onFailed = {
                                    Log.d("BSA ERROR", it?.errorMessage.toString())
                                    loading = false
                                    hasErrorOtp
                                })
                        })
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.create))
                }
            } else {
                Loader()
            }
            if (hasErrorDuplicate) {
                ErrorMessage(message = "This email address is not valid")
            }
            if (hasErrorOtp) {
                ErrorMessage("An error occur when we try to send Otp code")
            }
            Spacer(modifier = Modifier.size(24.dp))
            TextButton(onClick = { onCancleButtonClicked() }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.have_account))
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onCreateButtonClicked = {}, onCancleButtonClicked = {})
}