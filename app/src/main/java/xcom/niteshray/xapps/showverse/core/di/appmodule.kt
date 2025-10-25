package xcom.niteshray.xapps.showverse.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xcom.niteshray.xapps.showverse.core.util.Constants
import xcom.niteshray.xapps.showverse.data.remote.WatchmodeApiService
import xcom.niteshray.xapps.showverse.data.repository.MovieRepositoryImpl
import xcom.niteshray.xapps.showverse.domain.repository.MovieRepository
import xcom.niteshray.xapps.showverse.domain.usecase.GetMovieDetailUseCase
import xcom.niteshray.xapps.showverse.domain.usecase.GetMoviesAndTVShowsUseCase
import xcom.niteshray.xapps.showverse.presentation.detail.DetailViewModel
import xcom.niteshray.xapps.showverse.presentation.home.HomeViewModel
import java.util.concurrent.TimeUnit

/**
 * Network module - provides networking dependencies
 */
val networkModule = module {
    // Gson
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }
    
    // OkHttpClient
    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    // Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    
    // API Service
    single<WatchmodeApiService> {
        get<Retrofit>().create(WatchmodeApiService::class.java)
    }
}

/**
 * Repository module - provides data layer implementations
 */
val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }
}

/**
 * Domain module - provides use cases
 */
val domainModule = module {
    factory { GetMoviesAndTVShowsUseCase(get()) }
    factory { GetMovieDetailUseCase(get()) }
}

/**
 * Presentation module - provides ViewModels
 */
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val appModule = listOf(
    networkModule,
    repositoryModule,
    domainModule,
    viewModelModule,
)

