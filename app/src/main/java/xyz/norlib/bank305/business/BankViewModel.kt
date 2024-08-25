package xyz.norlib.bank305.business

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import xyz.norlib.bank305.business.api.BankApi
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface BankUiState {
    data class Success(val photos: String) : BankUiState
    object Error : BankUiState
    object Loading : BankUiState
}

class BankViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var bankUiState: BankUiState by mutableStateOf(BankUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getUserData()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     */
    fun getUserData() {
        viewModelScope.launch {
            bankUiState = BankUiState.Loading
            bankUiState = try {
                val listResult = BankApi.retrofitService.getUser()
                BankUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                BankUiState.Error
            } catch (e: HttpException) {
                BankUiState.Error
            }
        }
    }
}