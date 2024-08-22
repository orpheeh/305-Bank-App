package xyz.norlib.bank305.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.norlib.bank305.R
import xyz.norlib.bank305.data.User
import xyz.norlib.bank305.ui.components.IntroductionMessage
import xyz.norlib.bank305.ui.components.OTPTextField

@Composable
fun OTPScreen(
    modifier: Modifier = Modifier,
    onSendButtonClicked: (String) -> Unit,
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
            onResendButtonClicked = onResendButtonClicked
        )
    }
}

@Composable
fun OTPForm(
    modifier: Modifier = Modifier,
    onSendButtonClicked: (String) -> Unit,
    onResendButtonClicked: () -> Unit
) {
    var otp by remember { mutableStateOf("") }

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
            label = { Text(stringResource(id = R.string.otp_field)) }
        )
        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = { onSendButtonClicked(otp) }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.send_btn))
        }

        Spacer(modifier = Modifier.size(24.dp))

        TextButton(onClick = { onResendButtonClicked() }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.resend_btn))
        }
    }
}

@Preview
@Composable
fun OTPScreenPreview() {
    OTPScreen(
        onSendButtonClicked = {},
        onResendButtonClicked = {},
        user = User(userKey = "test", name = "Linda", email = "email", phone = "phone")
    )
}