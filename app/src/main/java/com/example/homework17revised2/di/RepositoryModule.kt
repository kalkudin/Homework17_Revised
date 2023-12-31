package com.example.homework17revised2.di

import com.example.homework17revised2.data.login.implementation.LoginRepositoryImpl
import com.example.homework17revised2.data.login.service.LoginService
import com.example.homework17revised2.data.register.implementation.RegisterRepositoryImpl
import com.example.homework17revised2.data.register.service.RegisterService
import com.example.homework17revised2.domain.login.LoginRepository
import com.example.homework17revised2.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideLoginRepository(loginService : LoginService) : LoginRepository {
        return LoginRepositoryImpl(loginService = loginService)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(registerService : RegisterService) : RegisterRepository{
        return RegisterRepositoryImpl(registerService = registerService)
    }
}