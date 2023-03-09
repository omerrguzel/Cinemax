package com.example.cinemax.di

import com.example.cinemax.domain.repository.AuthRepository
import com.example.cinemax.domain.repository.AuthRepositoryImp
import com.example.cinemax.domain.usecase.auth.SignInWithCredentialUseCase
import com.example.cinemax.utils.AuthOperationHelper
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseUser() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImp(auth)
    }

    @Provides
    @Singleton
    fun provideSignInWithCredentialUseCase(
        authRepository: AuthRepository
    ): SignInWithCredentialUseCase {
        return SignInWithCredentialUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideAuthOperationHelper(firebaseAuth: FirebaseAuth) =
        AuthOperationHelper(firebaseAuth)

}