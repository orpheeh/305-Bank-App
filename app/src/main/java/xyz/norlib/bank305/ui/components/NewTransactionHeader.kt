package xyz.norlib.bank305.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppTypography
import xyz.norlib.bank305.R

@Composable
fun NewTransactionHeader(
    @StringRes transactionType: Int,
    @StringRes message: Int,
    color: Color
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.new_transaction), style = AppTypography.titleLarge)
        Text(
            stringResource(id = transactionType),
            textAlign = TextAlign.Center,
            style = AppTypography.titleMedium,
            color = color
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            stringResource(id = message),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = AppTypography.bodyLarge
        )
    }
}