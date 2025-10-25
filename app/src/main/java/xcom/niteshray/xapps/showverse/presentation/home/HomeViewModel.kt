package xcom.niteshray.xapps.showverse.presentation.home

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xcom.niteshray.xapps.showverse.core.util.ApiResult
import xcom.niteshray.xapps.showverse.data.model.ContentItem
import xcom.niteshray.xapps.showverse.domain.usecase.GetMoviesAndTVShowsUseCase

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(
        val movies: List<ContentItem>,
        val tvShows: List<ContentItem>,
        val selectedTab: ContentTab = ContentTab.MOVIES
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

enum class ContentTab {
    MOVIES, TV_SHOWS
}

/**
 * ViewModel for Home Screen
 * Uses GetMoviesAndTVShowsUseCase to fetch data
 */
class HomeViewModel(
    private val getMoviesAndTVShowsUseCase: GetMoviesAndTVShowsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private val compositeDisposable = CompositeDisposable()
    
    init {
        loadContent()
    }
    
    fun loadContent() {
        _uiState.value = HomeUiState.Loading
        
        // Using the use case to fetch data with ApiResult
        val disposable = getMoviesAndTVShowsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val (movies, tvShows) = result.data
                        _uiState.value = HomeUiState.Success(
                            movies = movies,
                            tvShows = tvShows
                        )
                    }
                    is ApiResult.Error -> {
                        _uiState.value = HomeUiState.Error(
                            message = result.message
                        )
                    }
                    is ApiResult.Loading -> {
                        _uiState.value = HomeUiState.Loading
                    }
                }
            }
        
        compositeDisposable.add(disposable)
    }
    
    fun selectTab(tab: ContentTab) {
        _uiState.update { currentState ->
            if (currentState is HomeUiState.Success) {
                currentState.copy(selectedTab = tab)
            } else {
                currentState
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
