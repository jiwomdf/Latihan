package com.katilijiwo.latihan.di

import com.katilijiwo.latihan.data.Repository
import com.katilijiwo.latihan.data.RepositoryImpl
import com.katilijiwo.latihan.data.remote.service.UsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(usersService: UsersService): Repository {
        return RepositoryImpl(usersService)
    }

}