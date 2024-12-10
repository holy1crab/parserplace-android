package ru.tinyad.parserplace.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tinyad.parserplace.core.data.DefaultProductSubscriptionRepository
import ru.tinyad.parserplace.core.data.ProductSubscriptionRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds()
    internal abstract fun bindsProductSubscriptionRepository(
        subscriptionRepository: DefaultProductSubscriptionRepository
    ): ProductSubscriptionRepository
}