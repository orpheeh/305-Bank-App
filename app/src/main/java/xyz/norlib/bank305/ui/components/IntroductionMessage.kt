package xyz.norlib.bank305.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppTypography
import xyz.norlib.bank305.R

@Composable
fun IntroductionMessage(
    @StringRes title: Int,
    @StringRes message: Int,
    titleData: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(id = title, titleData),
            style = AppTypography.displayMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            stringResource(id = message),
            style = AppTypography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun IntroductionMessagePreview() {
    IntroductionMessage(title = R.string.welcome_title, message = R.string.welcome_message)
}