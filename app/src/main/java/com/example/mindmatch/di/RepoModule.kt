package com.example.mindmatch.di

import com.example.mindmatch.domain.AppRepository
import com.example.mindmatch.domain.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindAppRepo(impl: AppRepositoryImpl): AppRepository
}