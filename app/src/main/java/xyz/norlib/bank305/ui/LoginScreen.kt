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

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: (String) -> Unit,
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
    onLoginButtonClicked: (String) -> Unit,
    onCancleButtonClicked: () -> Unit
) {
    var userKey by remember { mutableStateOf("") }

    Column(modifier.padding(24.dp)) {
        TextField(
            value = userKey,
            onValueChange = { },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(stringResource(id = R.string.user_key)) }
        )

        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = { onLoginButtonClicked(userKey) }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.signin_btn))
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