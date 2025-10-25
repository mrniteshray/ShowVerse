package xcom.niteshray.xapps.showverse.core.util

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}

