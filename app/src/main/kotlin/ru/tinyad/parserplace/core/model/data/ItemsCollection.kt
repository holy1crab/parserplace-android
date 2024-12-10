package ru.tinyad.parserplace.core.model.data

interface ItemsCollection<T> {
    val items: List<T>
    val total: Number
}