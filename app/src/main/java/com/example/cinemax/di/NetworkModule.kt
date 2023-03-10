package com.example.cinemax.di

import androidx.viewbinding.BuildConfig
import com.example.cinemax.data.remote.MovieApiService
import com.example.cinemax.data.remote.RemoteDataSource
import com.example.cinemax.data.remote.repository.MovieRepositoryImp
import com.example.cinemax.domain.repository.MovieRepository
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
class NetworkModule {

    @Provides
    fun provideApiService(@NetworkRetrofit retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Provides
    @NetworkRetrofit
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        endPoint: EndPoint
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        return builder.build()
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideEndPoint(): EndPoint {
        return EndPoint("https://api.themoviedb.org/3/")
    }

    @Provides
    fun provideRemoteDataSource(
        networkApiService: MovieApiService,
    ): RemoteDataSource {
        return RemoteDataSource(networkApiService)
    }

    @Provides
    fun provideMovieRepository(remoteDataSource: RemoteDataSource): MovieRepository {
        return MovieRepositoryImp(remoteDataSource)
    }

}

data class EndPoint(val url: String)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkRetrofit