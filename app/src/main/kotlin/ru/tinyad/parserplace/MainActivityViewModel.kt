package ru.tinyad.parserplace

import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tinyad.parserplace.core.Result
import ru.tinyad.parserplace.core.data.PreviewAndParameters
import ru.tinyad.parserplace.core.model.data.UserData
import ru.tinyad.parserplace.core.network.ProductApi

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val productApi: ProductApi
) : ViewModel() {

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
