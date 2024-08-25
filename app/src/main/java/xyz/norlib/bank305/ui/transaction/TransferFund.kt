package xyz.norlib.bank305.ui.transaction

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.norlib.bank305.R
import xyz.norlib.bank305.business.BankUiState
import xyz.norlib.bank305.business.model.TransactionModel
import xyz.norlib.bank305.data.RegistrationData
import xyz.norlib.bank305.data.fakeUser
import xyz.norlib.bank305.integration.BsaUserInformation
import xyz.norlib.bank305.ui.bankViewModel
import xyz.norlib.bank305.ui.components.InAppAuthentication
import xyz.norlib.bank305.ui.components.Loader
import xyz.norlib.bank305.ui.components.NewTransactionHeader

@Composable
fun TransferFund(
    onCancel: () -> Unit,
    onCOnfirm: () -> Unit
) {

    var loading by remember { mutableStateOf(true) }
    var hasAuthError by remember { mutableStateOf(false) }
    var registrationData by remember {
        mutableStateOf(RegistrationData(user = fakeUser, authType = "", disposeToken = ""))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NewTransactionHeader(
            transactionType = R.string.transfer_fund,
            message = R.string.transfer_fund_message,
            color = colorResource(
                id = R.color.bank_blue
            )
        )
        Spacer(modifier = Modifier.size(50.dp))
        if (!loading && !hasAuthError) {
            TransferForm(registrationData = registrationData, onValidateButtonClicked = {
                onCOnfirm()
            })
        }
        if (hasAuthError) {
            Text("Authentication failed !")
        }
        if (loading) {
            InAppAuthentication(loading = loading, onSuccess = {
                BsaUserInformation.deviceCheck {
                    registrationData = it
                    loading = false
                }
            }, onFailure = {
                Log.d("BSA Add fun check", "Error")
                hasAuthError = true
                loading = false
            })
        }
        OutlinedButton(onClick = { onCancel() }) {
            Text("Cancel")
        }
    }
}

@Composable
fun TransferForm(onValidateButtonClicked: () -> Unit, registrationData: RegistrationData) {
    var account by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var startTransaction by remember { mutableStateOf(false) }

    Column(
        Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = account,
            onValueChange = { account = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(stringResource(id = R.string.destinataire_email)) }
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(stringResource(id = R.string.name)) }
        )
        TextField(
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(id = R.string.amount)) }
        )

        Spacer(modifier = Modifier.size(24.dp))

        if (startTransaction) {
            startTransaction = false
            bankViewModel.createTransaction(
                TransactionModel(
                    user = registrationData.user.userKey,
                    amount = amount.toInt(),
                    destinataire = name,
                    destinataireDetail = account,
                    type = "TRANSFER",
                    name = account,
                    createdAt = null
                )
            )
        } else {
            Button(onClick = {
                loading = true
                startTransaction = true
            }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.validate_btn))
            }
        }

        when (bankViewModel.bankUiState) {
            is BankUiState.Loading -> Loader()
            is BankUiState.SuccessCreateTransaction -> onValidateButtonClicked()
            is BankUiState.Error -> Text(
                "Failed to createTransaction",
                modifier = Modifier.fillMaxSize()
            )

            else -> Text("")
        }
    }
}

@Preview
@Composable
fun TransferPreview() {
    TransferFund({}, {})
}