package xcom.niteshray.xapps.showverse.domain.usecase

import io.reactivex.rxjava3.core.Single
import xcom.niteshray.xapps.showverse.core.util.ApiResult
import xcom.niteshray.xapps.showverse.data.model.ContentDetail
import xcom.niteshray.xapps.showverse.domain.repository.MovieRepository

/**
 * Use case for fetching movie/TV show details
 * Encapsulates business logic for fetching detailed information
 */
class GetMovieDetailUseCase(
    private val repository: MovieRepository
) {
    /**
     * Execute the use case
     * @param titleId The ID of the movie/TV show
     * @return Single with ApiResult containing movie details
     */
    operator fun invoke(titleId: Int): Single<ApiResult<ContentDetail>> {
        return repository.getMovieDetail(titleId)
            .map<ApiResult<ContentDetail>> { data ->
                ApiResult.Success(data)
            }
            .onErrorReturn { error ->
                ApiResult.Error(
                    message = error.message ?: "Failed to load details",
                    throwable = error
                )
            }
    }
}

