package ru.tinyad.parserplace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import ru.tinyad.parserplace.core.network.DefaultProductApi
import ru.tinyad.parserplace.core.network.ProductApi
import ru.tinyad.parserplace.core.network.getHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(): HttpClient = getHttpClient()

    @Provides
    fun provideProductApi(httpClient: HttpClient): ProductApi = DefaultProductApi(httpClient)
}