package ru.tinyad.parserplace.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class NumberOption(
    val value: Int,
    val text: String,
)