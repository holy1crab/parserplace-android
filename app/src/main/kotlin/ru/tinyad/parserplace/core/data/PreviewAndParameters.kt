package ru.tinyad.parserplace.core.data

import ru.tinyad.parserplace.core.model.data.NumberOption

data class SubscriptionItemParameter(
    val key: String,
    val title: String,
    val multiple: Boolean,
    val options: List<NumberOption>,
)

data class PreviewAndParameters(
    val url: String,
    val parameters: List<SubscriptionItemParameter>,
    val product: Product,
)