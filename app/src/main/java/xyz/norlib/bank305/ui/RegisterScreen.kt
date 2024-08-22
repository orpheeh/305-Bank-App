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
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(modifier.padding(24.dp)) {
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
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(stringResource(id = R.string.email)) }
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(stringResource(id = R.string.phone_number)) }
        )

        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = {
            onCreateButtonClicked(
                User(
                    userKey = userKey,
                    name = name,
                    email = email,
                    phone = phoneNumber
                )
            )
        }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.create))
        }

        Spacer(modifier = Modifier.size(16.dp))

        TextButton(onClick = { onCancleButtonClicked() }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.have_account))
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onCreateButtonClicked = {}, onCancleButtonClicked = {})
}