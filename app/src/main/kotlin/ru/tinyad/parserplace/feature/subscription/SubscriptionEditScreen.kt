package ru.tinyad.parserplace.feature.subscription

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ru.tinyad.parserplace.R
import ru.tinyad.parserplace.core.data.CreateOrEditSubscription
import ru.tinyad.parserplace.core.data.Product
import ru.tinyad.parserplace.ui.theme.Pink80
import ru.tinyad.parserplace.ui.theme.PurpleGrey80

@Composable
fun SubscriptionEditScreen(viewModel: SubscriptionEditViewModel = hiltViewModel()) {
    val uiState by viewModel.urlAddUiState
//    val
    val buttonAddDisabled = viewModel.buttonAddDisabled
    val url by viewModel.url.collectAsStateWithLifecycle()
    SubscriptionEditScreen(
        uiState = uiState,
        buttonSaveDisabled = buttonAddDisabled,
        toggleButtonAddDisabled = viewModel::toggleButtonAddDisabled,
        url = url,
        onUrlChanged = viewModel::onUrlChanged,
        onUrlPreview = viewModel::getProductPreview,
        onSaveSubscription = viewModel::onSaveSubscription
    )
}

@VisibleForTesting
@Composable
internal fun SubscriptionEditScreen(
    uiState: UrlAddUiState,
    buttonSaveDisabled: Boolean,
    toggleButtonAddDisabled: () -> Unit,
    url: String,
    onUrlChanged: (String) -> Unit,
    onUrlPreview: (String) -> Unit,
    onSaveSubscription: (obj: CreateOrEditSubscription) -> Unit,
) {

//    val obj: Subscription
}

@Composable
fun UserSubscriptionItem(
    uiState: UrlAddUiState,
) {

    val isLoading = uiState === UrlAddUiState.Loading

    Column {

        TextField(
            value = "",
            label = { Text(stringResource(R.string.subscription_add_button)) },
            onValueChange = {
//                onUrlChanged(it)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
//                onUrlPreview(url)
            },
            enabled = !isLoading
        ) {
            Text("Send")
        }

        when (uiState) {
            UrlAddUiState.Initial -> {}

            UrlAddUiState.Empty -> {
                Text("Nothing found")
            }

            UrlAddUiState.Loading -> {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            is UrlAddUiState.Error -> {
                Text("Something went wrong")
            }

            is UrlAddUiState.ProductPreview -> {

                val product = uiState.previewAndParameters.product
                val parameters = uiState.previewAndParameters.parameters

                SubscriptionProductPreview(
                    modifier = Modifier,
                    product = product,
                )

                if (parameters.isNotEmpty()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Box {
                            Text(stringResource(R.string.subscription_add_resolve_parameters))
                        }

                        parameters.forEachIndexed { index, parameter ->
                            SubscriptionProductParameter(
                                index = index,
                                parameter = parameter,
                                optionsSelected = { _, _ -> },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(border = BorderStroke(1.dp, Pink80))
                            )
                        }
                    }
                }

                OutlinedButton(
//                    enabled = !buttonSaveDisabled,
                    enabled = true,
                    onClick = {
//                        onSaveSubscription()
                    },
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(stringResource(R.string.subscription_add_confirm))
                }
            }
        }
    }
}

@Preview
@Composable
fun SubscriptionAddScreenPreview() {

    SubscriptionEditScreen(
        uiState = UrlAddUiState.Loading,
        url = "",
        buttonSaveDisabled = true,
        toggleButtonAddDisabled = {},
        onUrlChanged = {},
        onUrlPreview = {},
        onSaveSubscription = {}
    )
}

@Composable
fun SubscriptionProductPreview(
    modifier: Modifier = Modifier,
    product: Product,
) {

    Row(
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = product.coverImage,
                contentDescription = "Product's preview image",
                error = painterResource(R.drawable.baseline_hide_image_24)
            )
        }
        Column(modifier = Modifier.padding(top = 8.dp, end = 8.dp, bottom = 8.dp)) {
            Text(
                text = stringResource(if (product.inStock) R.string.com_in_stock else R.string.com_out_of_stock),
                style = TextStyle(
                    fontSize = 10.sp,
                    color = if (product.inStock) Color.Green else Color.Red
                )
            )
            Text(product.title)
            Text("${product.price} ₽")
        }

    }
}

@Preview
@Composable
fun SubscriptionProductPreviewPreview() {

    val product = Product(
        title = "My item 20l, blue, beautiful 17195",
        coverImage = "still-error.jpg",
        price = 760f,
        inStock = true
    )

    SubscriptionProductPreview(product = product)
}

@Preview
@Composable
fun SampleButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PurpleGrey80),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedButton(
            onClick = {
            }, modifier = Modifier
                .padding(top = 12.dp, bottom = 24.dp)
        ) {
            Text(stringResource(R.string.com_submit))
        }
    }
}
