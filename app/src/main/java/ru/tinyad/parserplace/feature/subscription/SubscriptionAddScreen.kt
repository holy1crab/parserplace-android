package ru.tinyad.parserplace.feature.subscription

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ru.tinyad.parserplace.R
import ru.tinyad.parserplace.core.data.Product
import ru.tinyad.parserplace.core.data.SubscriptionItemParameter

@Composable
fun SubscriptionAddScreen(viewModel: SubscriptionAddViewModel = hiltViewModel()) {
    val uiState by viewModel.subscriptionAddUiState
    val url by viewModel.url.collectAsStateWithLifecycle()
    SubscriptionAddScreen(
        uiState = uiState,
        url = url,
        onUrlChanged = viewModel::onUrlChanged,
        onUrlPreview = viewModel::getProductPreview
    )
}

@VisibleForTesting
@Composable
internal fun SubscriptionAddScreen(
    uiState: SubscriptionAddUiState,
    url: String,
    onUrlChanged: (String) -> Unit,
    onUrlPreview: (String) -> Unit,
) {

//    var text by remember(
//        calculation = fun(): String {
//            return mutableStateOf("")
//        })
//    var url by remember { mutableStateOf("") }
//val uiState by viewModel.uiState

    val urlAddMessage = stringResource(R.string.subscription_add_button)

    val isLoading = uiState === SubscriptionAddUiState.Loading

    Column {

        TextField(
            value = url,
            label = { Text(urlAddMessage) },
            onValueChange = { onUrlChanged(it) }
        )

        Button(
            onClick = {
                onUrlPreview(url)
            },
            enabled = !isLoading
        ) {
            Text("Send")
        }

        when (uiState) {
            SubscriptionAddUiState.Initial -> {

            }

            SubscriptionAddUiState.Empty -> {
                Text("Nothing found")
            }

            SubscriptionAddUiState.Loading -> {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            is SubscriptionAddUiState.Error -> {
                Text("Something went wrong")
            }

            is SubscriptionAddUiState.ProductPreview -> {

                val (urlTest, parameters, product) = uiState.previewAndParameters
                println("$urlTest $parameters $product")

                SubscriptionProductPreview(
                    modifier = Modifier,
                    product = product,
                )
            }
        }
    }
}

@Composable
fun SubscriptionProductPreview(
    modifier: Modifier = Modifier,
    product: Product,
) {

    Row {
        AsyncImage(
            model = product.coverImage,
            contentDescription = "Product's preview image"
        )
        Column {
            Text(stringResource(if (product.inStock) R.string.com_in_stock else R.string.com_out_of_stock))
            Text(product.title)
            Text("${product.price} ₽")
        }

    }
}