package ru.tinyad.parserplace.feature.subscription

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tinyad.parserplace.core.data.PreviewAndParameters
import ru.tinyad.parserplace.core.data.ProductSubscriptionRepository

private const val URL_KEY = "subscription.url"

private const val TAG = "SubscriptionAddViewModel"

@HiltViewModel
class SubscriptionAddViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productSubscriptionRepository: ProductSubscriptionRepository,
) : ViewModel() {

    val initialUrl =
        "https://www.vseinstrumenti.ru/product/montirovka-forsage-kryuk-l-850-mm-diametr-19-mm-6-000850n-f-67701s-9486242/"

    val url = savedStateHandle.getStateFlow(URL_KEY, initialValue = initialUrl)

    var subscriptionAddUiState: MutableState<SubscriptionAddUiState> = mutableStateOf(
        SubscriptionAddUiState.Initial
    )

//    val uiState: Flow<SubscriptionAddUiState> = productSubscriptionRepository
//        .getPreviewAndParameters(arrayOf("sample url"))
//        .map()
//        .stateIn(
//            scope = viewModelScope,
//            started = WhileSubscribed(5000L),
//            initialValue = SubscriptionAddUiState.Loading
//        )

//    private val _productPreview = MutableStateFlow<Result<PreviewAndParameters>>(Result.Loading)
//    val productPreview = _productPreview.asStateFlow()

//    val subscriptionAddUiState

    fun onUrlChanged(value: String) {
        savedStateHandle[URL_KEY] = value
    }

    fun getProductPreview(url: String) {
        viewModelScope.launch {
            subscriptionAddUiState.value = SubscriptionAddUiState.Loading
            productSubscriptionRepository.getPreviewAndParameters(arrayOf(url))
                .catch { err ->
                    subscriptionAddUiState.value = SubscriptionAddUiState.Error(err)
                }
                .onStart {
                    subscriptionAddUiState.value = SubscriptionAddUiState.Loading
                }
                .collect {
                    subscriptionAddUiState.value =
                        if (it.isEmpty()) SubscriptionAddUiState.Empty
                        else SubscriptionAddUiState.ProductPreview(it[0])
                }
        }
    }
}

sealed interface SubscriptionAddUiState {
    data object Initial : SubscriptionAddUiState
    data object Loading : SubscriptionAddUiState
    data object Empty : SubscriptionAddUiState

    data class ProductPreview(val previewAndParameters: PreviewAndParameters) :
        SubscriptionAddUiState

    data class Error(val cause: Throwable) : SubscriptionAddUiState
}