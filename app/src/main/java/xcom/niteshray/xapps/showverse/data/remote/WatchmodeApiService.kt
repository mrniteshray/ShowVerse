package xcom.niteshray.xapps.showverse.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xcom.niteshray.xapps.showverse.data.model.ContentDetail
import xcom.niteshray.xapps.showverse.data.model.ContentResponse

interface WatchmodeApiService {

    @GET("list-titles/")
    fun getContentList(
        @Query("types") types: String,
        @Query("apiKey") apiKey: String,
        @Query("limit") limit: Int = 20
    ): Single<ContentResponse>

    @GET("title/{title_id}/details/")
    fun getContentDetail(
        @Path("title_id") titleId: Int,
        @Query("apiKey") apiKey: String
    ): Single<ContentDetail>
}
