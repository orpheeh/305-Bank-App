package xyz.norlib.bank305.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schema
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppTypography
import xyz.norlib.bank305.R
import xyz.norlib.bank305.data.User
import xyz.norlib.bank305.ui.components.IntroductionMessage
import xyz.norlib.bank305.ui.components.OTPTextField

@Composable
fun BiometryScreen(
    modifier: Modifier = Modifier,
    onSendButtonClicked: () -> Unit,
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
        )
    }
}

@Composable
fun BiometryForm(
    modifier: Modifier = Modifier,
    onSendButtonClicked: () -> Unit,
) {
    Column(
        modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomRadioGroup()
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = { onSendButtonClicked() }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.validate_btn))
        }
    }
}

@Composable
fun CustomRadioGroup() {
    val radioOptions = listOf("Biometry", "Schema")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }

    Column {
        radioOptions.forEach { option ->
            Row(
                Modifier
                    .clickable { selectedOption = option }
                    .padding(8.dp)
                    .border(
                        color = if (option == selectedOption) colorResource(id = R.color.bank_green) else Color.Gray,
                        width = 1.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (option == selectedOption) {
                    Icon(
                        Icons.Default.Fingerprint,
                        contentDescription = "Selected option",
                        modifier = Modifier
                            .size(64.dp)
                            .padding(16.dp),
                        tint = colorResource(id = R.color.bank_green)
                    )
                } else {
                    Icon(
                        Icons.Default.Schema,
                        contentDescription = "Unselected option",
                        modifier = Modifier
                            .size(64.dp)
                            .padding(16.dp),
                        tint = Color.Gray
                    )
                }
                Text(
                    text = option,
                    style = AppTypography.titleLarge,
                    color = if (option == selectedOption) colorResource(id = R.color.bank_green) else Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun BiometryScreenPreview() {
    BiometryScreen(
        onSendButtonClicked = {},
    )
}