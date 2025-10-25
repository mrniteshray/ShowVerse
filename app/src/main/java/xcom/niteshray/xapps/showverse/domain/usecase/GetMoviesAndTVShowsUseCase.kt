package xcom.niteshray.xapps.showverse.domain.usecase

import io.reactivex.rxjava3.core.Single
import xcom.niteshray.xapps.showverse.core.util.ApiResult
import xcom.niteshray.xapps.showverse.data.model.ContentItem
import xcom.niteshray.xapps.showverse.domain.repository.MovieRepository

/**
 * Use case for fetching movies and TV shows simultaneously
 * Encapsulates business logic for this specific operation
 */
class GetMoviesAndTVShowsUseCase(
    private val repository: MovieRepository
) {
    /**
     * Execute the use case
     * @return Single with ApiResult containing pair of movies and TV shows
     */
    operator fun invoke(): Single<ApiResult<Pair<List<ContentItem>, List<ContentItem>>>> {
        return repository.getMoviesAndTVShows()
            .map<ApiResult<Pair<List<ContentItem>, List<ContentItem>>>> { data ->
                ApiResult.Success(data)
            }
            .onErrorReturn { error ->
                ApiResult.Error(
                    message = error.message ?: "Unknown error occurred",
                    throwable = error
                )
            }
    }
}

