package com.ns.fakex.di

import com.ns.fakex.data.repository.ApiRepository
import com.ns.fakex.data.repository.ApiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindApiRepository(sourceImpl: ApiRepositoryImpl): ApiRepository

}
