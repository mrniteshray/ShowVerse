package xcom.niteshray.xapps.showverse.domain.repository

import io.reactivex.rxjava3.core.Single
import xcom.niteshray.xapps.showverse.data.model.ContentItem
import xcom.niteshray.xapps.showverse.data.model.ContentDetail

/**
 * Repository interface in domain layer
 * Defines contracts for data operations without knowing implementation details
 */
interface MovieRepository {
    
    /**
     * Fetch both movies and TV shows simultaneously
     * @return Pair of movies list and TV shows list
     */
    fun getMoviesAndTVShows(): Single<Pair<List<ContentItem>, List<ContentItem>>>
    
    /**
     * Fetch only movies
     * @return List of movies
     */
    fun getMovies(): Single<List<ContentItem>>
    
    /**
     * Fetch only TV shows
     * @return List of TV shows
     */
    fun getTVShows(): Single<List<ContentItem>>
    
    /**
     * Get detailed information about a specific movie/TV show
     * @param titleId The ID of the content
     * @return Detailed movie/TV show information
     */
    fun getMovieDetail(titleId: Int): Single<ContentDetail>
}
