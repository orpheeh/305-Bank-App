package xyz.norlib.bank305.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppTypography
import xyz.norlib.bank305.R
import xyz.norlib.bank305.business.api.BankApi
import xyz.norlib.bank305.data.Transaction
import xyz.norlib.bank305.data.fakeTansactions
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import xyz.norlib.bank305.business.BankUiState
import xyz.norlib.bank305.business.BankViewModel
import xyz.norlib.bank305.ui.components.Loader

@Composable
fun HomeScreen(
    onUserButtonClicked: () -> Unit,
    add: () -> Unit,
    remove: () -> Unit,
    transfer: () -> Unit
) {
    val bankViewModel: BankViewModel = viewModel()
    when (bankViewModel.bankUiState) {
        is BankUiState.Loading -> Loader()
        is BankUiState.Success -> Text(
            bankViewModel.bankUiState.toString(), modifier = Modifier.fillMaxWidth()
        )
        is BankUiState.Error -> Text("Failed to load your data", modifier = Modifier.fillMaxSize())
    }
    Column {
        Balance(onUserButtonClicked = onUserButtonClicked)
        Menu(add, remove, transfer)
        Text(
            stringResource(id = R.string.transaction_history),
            style = AppTypography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        TransactionHistory(transactions = fakeTansactions)
    }
}

@Composable
fun Balance(modifier: Modifier = Modifier, onUserButtonClicked: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.bank_blue)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                "1,235,265", style = AppTypography.displaySmall,
                color = Color.White
            )
            Text(
                stringResource(id = R.string.balance), style = AppTypography.bodyLarge,
                color = Color.White
            )
        }
        Column(
            modifier = modifier.padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = { onUserButtonClicked() }) {
                Icon(
                    imageVector = Icons.Default.ManageAccounts,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                stringResource(id = R.string.currency), style = AppTypography.bodyLarge,
                color = Color.Gray,
            )
        }

    }
}

@Composable
fun Menu(
    add: () -> Unit,
    remove: () -> Unit,
    transfer: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MenuItem(label = stringResource(id = R.string.add_fund), onClick = { add() })
        MenuItem(label = stringResource(id = R.string.remove_fund), onClick = { remove() })
        MenuItem(label = stringResource(id = R.string.transfer_fund), onClick = { transfer() })
    }
}

@Composable
fun MenuItem(label: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick) {
        Text(text = label)
    }
}

@Composable
fun TransactionHistory(transactions: List<Transaction>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(transactions.size) { it ->
            TransactionItem(transaction = transactions[it])
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(transaction.type, style = AppTypography.titleMedium, color = Color.Gray)
            Spacer(modifier = Modifier.width(50.dp))
            Column {
                Text(
                    transaction.amount.toString(), style = AppTypography.bodyLarge, color =
                    colorResource(id = R.color.bank_blue)
                )
                Text(transaction.date)
            }
            Spacer(modifier = Modifier.width(50.dp))
            Column {
                Text(transaction.destinataire, style = AppTypography.bodyMedium)
                Text(transaction.destinataireDetail)
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}, {}, {})
}