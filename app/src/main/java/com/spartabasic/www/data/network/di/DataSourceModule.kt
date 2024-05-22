package com.spartabasic.www.data.network.di

import com.spartabasic.www.data.network.source.CataasNetworkDataSource
import com.spartabasic.www.data.network.source.RetrofitCataasNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRetrofitCataasNetwork(
        retrofitCataasNetwork: RetrofitCataasNetwork
    ): CataasNetworkDataSource
}