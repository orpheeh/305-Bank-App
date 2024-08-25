package xyz.norlib.bank305.business

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import xyz.norlib.bank305.business.api.BankApi
import xyz.norlib.bank305.business.model.TransactionModel
import xyz.norlib.bank305.business.model.UserModel
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface BankUiState {
    data class Success(val user: UserModel) : BankUiState
    data class SuccessCreateTransaction(val transaction: TransactionModel) : BankUiState
    data class SuccessCreateUser(val transaction: UserModel) : BankUiState
    data class SuccessGetTransaction(val transactions: List<TransactionModel>) : BankUiState
    object Error : BankUiState
    object Loading : BankUiState
}

class BankViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var bankUiState: BankUiState by mutableStateOf(BankUiState.Loading)
        private set

    /**
     * Gets Current User Data
     */
    fun getUserData(userKey: String) {
        viewModelScope.launch {
            bankUiState = BankUiState.Loading
            bankUiState = try {
                val user = BankApi.retrofitService.getUser(userKey)
                BankUiState.Success(user)
            } catch (e: IOException) {
                BankUiState.Error
            } catch (e: HttpException) {
                BankUiState.Error
            }
        }
    }

    /**
     * Create Transaction
     */
    fun createTransaction(transactionModel: TransactionModel) {
        viewModelScope.launch {
            bankUiState = BankUiState.Loading
            bankUiState = try {
                val transaction = BankApi.retrofitService.createTransaction(transactionModel)
                BankUiState.SuccessCreateTransaction(transaction)
            } catch (e: IOException) {
                BankUiState.Error
            } catch (e: HttpException) {
                BankUiState.Error
            }
        }
    }

    /**
     * Create User
     */
    fun createUser(userModel: UserModel) {
        viewModelScope.launch {
            bankUiState = BankUiState.Loading
            bankUiState = try {
                val user = BankApi.retrofitService.createUser(userModel)
                BankUiState.SuccessCreateUser(user)
            } catch (e: IOException) {
                BankUiState.Error
            } catch (e: HttpException) {
                BankUiState.Error
            }
        }
    }

    /**
     * Get All Transactions for current user
     */
    fun getAllTransaction(user: String) {
        viewModelScope.launch {
            bankUiState = BankUiState.Loading
            bankUiState = try {
                val transactions = BankApi.retrofitService.getTransactions(user)
                BankUiState.SuccessGetTransaction(transactions)
            } catch (e: IOException) {
                BankUiState.Error
            } catch (e: HttpException) {
                BankUiState.Error
            }
        }
    }
}