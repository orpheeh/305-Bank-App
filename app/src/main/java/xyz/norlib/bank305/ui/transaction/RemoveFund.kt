package xyz.norlib.bank305.ui.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.norlib.bank305.R
import xyz.norlib.bank305.ui.components.NewTransactionHeader

@Composable
fun RemoveFund() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NewTransactionHeader(
            transactionType = R.string.remove_fund,
            message = R.string.remove_fund_message,
            color = Color.Red
        )
        Spacer(modifier = Modifier.size(50.dp))
        RemoveForm {

        }
    }
}

@Composable
fun RemoveForm(onValidateButtonClicked: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var mobileMoney by remember { mutableStateOf("") }
    var account by remember { mutableStateOf("") }

    Column(Modifier.padding(24.dp)) {
        TextField(
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(id = R.string.amount)) }
        )
        TextField(
            value = mobileMoney,
            onValueChange = { mobileMoney = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(stringResource(id = R.string.mobile_money)) }
        )
        TextField(
            value = account,
            onValueChange = { account = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(id = R.string.account)) }
        )

        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = {
            onValidateButtonClicked()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.validate_btn))
        }
    }
}

@Preview
@Composable
fun RemovePreview() {
    RemoveFund()
}