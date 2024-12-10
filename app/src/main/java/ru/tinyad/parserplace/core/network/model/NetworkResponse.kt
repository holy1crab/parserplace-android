package ru.tinyad.parserplace.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val response: T,
)

@Serializable
data class ResponseCollection<T>(
    val items: List<T>,
    val total: Int
)