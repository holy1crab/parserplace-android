package ru.tinyad.parserplace.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tinyad.parserplace.core.data.PreviewAndParameters
import ru.tinyad.parserplace.core.data.Product
import ru.tinyad.parserplace.core.data.SubscriptionItemParameter
import ru.tinyad.parserplace.core.model.data.NumberOption

@Serializable
data class NetworkBaseProduct(
    val title: String,
    @SerialName("cover_image")
    val coverImage: String,
    val price: Float,
    @SerialName("in_stock")
    val inStock: Boolean,
//    val payload: Map<String, Any>?,
)

fun NetworkBaseProduct.asExternalModel() = Product(
    title = title,
    coverImage = coverImage,
    price = price,
    inStock = inStock
)

@Serializable
data class NetworkSubscriptionItemParameter(
    val key: String,
    val title: String,
    val multiple: Boolean,
    val options: List<NumberOption>,
)

fun NetworkSubscriptionItemParameter.asExternalModel(): SubscriptionItemParameter =
    SubscriptionItemParameter(
        key = key,
        title = title,
        multiple = multiple,
        options = options
    )

@Serializable
data class NetworkPreviewAndParameters(
    val url: String,
    val parameters: List<NetworkSubscriptionItemParameter>,
    val product: NetworkBaseProduct
) {
    override fun toString(): String {
        return "NetworkPreviewAndParameters(url=$url)"
    }
}

fun NetworkPreviewAndParameters.asExternalModel(): PreviewAndParameters = PreviewAndParameters(
    url = url,
    parameters = parameters.map { it.asExternalModel() },
    product = product.asExternalModel(),
)
