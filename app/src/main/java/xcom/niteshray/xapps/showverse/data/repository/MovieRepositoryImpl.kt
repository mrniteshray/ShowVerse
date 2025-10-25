package xcom.niteshray.xapps.showverse.data.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Singles
import xcom.niteshray.xapps.showverse.core.util.Constants
import xcom.niteshray.xapps.showverse.data.model.ContentItem
import xcom.niteshray.xapps.showverse.data.model.ContentDetail
import xcom.niteshray.xapps.showverse.data.remote.WatchmodeApiService
import xcom.niteshray.xapps.showverse.domain.repository.MovieRepository

/**
 * Implementation of MovieRepository
 * Handles data fetching from remote API
 */
class MovieRepositoryImpl(
    private val apiService: WatchmodeApiService
) : MovieRepository {
    
    /**
     * Fetch both movies and TV shows simultaneously using Single.zip
     * This demonstrates the requirement for simultaneous API calls using RxKotlin
     */
    override fun getMoviesAndTVShows(): Single<Pair<List<ContentItem>, List<ContentItem>>> {
        val moviesCall = apiService.getContentList(
            types = Constants.TYPE_MOVIE,
            apiKey = Constants.API_KEY,
            limit = Constants.DEFAULT_LIMIT
        ).map { it.titles }
        
        val tvShowsCall = apiService.getContentList(
            types = Constants.TYPE_TV_SERIES,
            apiKey = Constants.API_KEY,
            limit = Constants.DEFAULT_LIMIT
        ).map { it.titles }
        
        // Using Single.zip to combine both API calls
        return Singles.zip(moviesCall, tvShowsCall) { movies, tvShows ->
            Pair(movies, tvShows)
        }
    }
    
    override fun getMovies(): Single<List<ContentItem>> {
        return apiService.getContentList(
            types = Constants.TYPE_MOVIE,
            apiKey = Constants.API_KEY,
            limit = Constants.DEFAULT_LIMIT
        ).map { it.titles }
    }
    
    override fun getTVShows(): Single<List<ContentItem>> {
        return apiService.getContentList(
            types = Constants.TYPE_TV_SERIES,
            apiKey = Constants.API_KEY,
            limit = Constants.DEFAULT_LIMIT
        ).map { it.titles }
    }
    
    override fun getMovieDetail(titleId: Int): Single<ContentDetail> {
        return apiService.getContentDetail(
            titleId = titleId,
            apiKey = Constants.API_KEY
        )
    }
}

