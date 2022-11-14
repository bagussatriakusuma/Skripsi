package com.example.ecommerceskripsi.DI

import com.example.ecommerceskripsi.Data.api.auth.AuthAPI
import com.example.ecommerceskripsi.Data.local.UserDAO
import com.example.ecommerceskripsi.Datastore.AuthDataStoreManager
import com.example.ecommerceskripsi.Repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        authDataStoreManager: AuthDataStoreManager,
        api: AuthAPI,
        dao: UserDAO
    ): AuthRepository {
        return AuthRepository(
            authDataStore = authDataStoreManager,
            api = api,
            dao = dao
        )
    }
}