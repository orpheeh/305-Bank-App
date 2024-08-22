package xyz.norlib.bank305.ui

import android.webkit.WebBackForwardList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.norlib.bank305.R
import xyz.norlib.bank305.ui.components.IntroductionMessage


@Composable
fun WelcomeScreen(
    modifier: Modifier, onCreateButtonClicked: () -> Unit,
    onSignInButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IntroductionMessage(
            title = R.string.welcome_title,
            message = R.string.welcome_message,
            modifier = modifier
        )
        WelcomeActionButton(
            modifier = modifier,
            onCreateButtonClicked = onCreateButtonClicked,
            onSignInButtonClicked = onSignInButtonClicked
        )
    }
}

@Composable
fun WelcomeActionButton(
    modifier: Modifier = Modifier, onCreateButtonClicked: () -> Unit,
    onSignInButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), onClick = { onCreateButtonClicked() }) {
            Text(stringResource(id = R.string.create_account_btn))
        }

        TextButton(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), onClick = { onSignInButtonClicked() }) {
            Text(stringResource(id = R.string.signin_btn))
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(modifier = Modifier, onCreateButtonClicked = {}, onSignInButtonClicked = {})
}