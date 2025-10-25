package xcom.niteshray.xapps.showverse.presentation.detail

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import xcom.niteshray.xapps.showverse.core.util.ApiResult
import xcom.niteshray.xapps.showverse.data.model.ContentDetail
import xcom.niteshray.xapps.showverse.domain.usecase.GetMovieDetailUseCase

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Success(val content: ContentDetail) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

/**
 * ViewModel for Detail Screen
 * Uses GetMovieDetailUseCase to fetch data
 */
class DetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    
    private val compositeDisposable = CompositeDisposable()
    
    fun loadContentDetail(titleId: Int) {
        _uiState.value = DetailUiState.Loading
        
        // Using the use case to fetch detail with ApiResult
        val disposable = getMovieDetailUseCase(titleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is ApiResult.Success -> {
                        _uiState.value = DetailUiState.Success(result.data)
                    }
                    is ApiResult.Error -> {
                        _uiState.value = DetailUiState.Error(
                            message = result.message
                        )
                    }
                    is ApiResult.Loading -> {
                        _uiState.value = DetailUiState.Loading
                    }
                }
            }
        
        compositeDisposable.add(disposable)
    }
    
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
