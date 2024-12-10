package ru.tinyad.parserplace.feature.subscription

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import javax.inject.Inject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.tinyad.parserplace.core.data.CreateOrEditSubscription
import ru.tinyad.parserplace.core.data.PreviewAndParameters
import ru.tinyad.parserplace.core.data.ProductSubscriptionRepository

private const val URL_KEY = "subscription.url"

private const val TAG = "SubscriptionAddViewModel"

@HiltViewModel
class SubscriptionEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productSubscriptionRepository: ProductSubscriptionRepository,
) : ViewModel() {

    val saveObject by mutableStateOf(CreateOrEditSubscription(items = mutableListOf()))

    val initialUrl =
        "https://www.vseinstrumenti.ru/product/montirovka-forsage-kryuk-l-850-mm-diametr-19-mm-6-000850n-f-67701s-9486242/"

    val url = savedStateHandle.getStateFlow(URL_KEY, initialValue = initialUrl)

    val buttonAddDisabled by mutableStateOf(true)

    var urlAddUiState: MutableState<UrlAddUiState> = mutableStateOf(
        UrlAddUiState.Initial
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

    fun toggleButtonAddDisabled() {
        buttonAddDisabled != buttonAddDisabled
    }

    fun onUrlChanged(value: String) {
        savedStateHandle[URL_KEY] = value
    }

    fun getProductPreview(url: String) {
        viewModelScope.launch {
            urlAddUiState.value = UrlAddUiState.Loading
            productSubscriptionRepository.getPreviewAndParameters(arrayOf(url))
                .catch { err ->
                    urlAddUiState.value = UrlAddUiState.Error(err)
                }
                .onStart {
                    urlAddUiState.value = UrlAddUiState.Loading
                }
                .collect {
                    urlAddUiState.value =
                        if (it.isEmpty()) UrlAddUiState.Empty
                        else UrlAddUiState.ProductPreview(it[0])
                }
        }
    }

    fun onSaveSubscription(obj: CreateOrEditSubscription) {
        viewModelScope.launch {
            productSubscriptionRepository.saveSubscription(obj)
        }
    }
}

sealed interface UrlAddUiState {
    data object Initial : UrlAddUiState
    data object Loading : UrlAddUiState
    data object Empty : UrlAddUiState

    data class ProductPreview(
        val previewAndParameters: PreviewAndParameters,
    ) : UrlAddUiState

    data class Error(val cause: Throwable) : UrlAddUiState
}
