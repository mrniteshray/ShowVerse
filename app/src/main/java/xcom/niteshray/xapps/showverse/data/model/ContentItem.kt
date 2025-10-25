package xcom.niteshray.xapps.showverse.data.model

import com.google.gson.annotations.SerializedName

data class ContentItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("year")
    val year: Int? = null,
    @SerializedName("plot_overview")
    val plotOverview: String? = null,
    @SerializedName("type")
    val type: String,
    @SerializedName("poster")
    val poster: String? = null,
    @SerializedName("backdrop")
    val backdrop: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("tmdb_id")
    val tmdbId: Int? = null,
    @SerializedName("tmdb_type")
    val tmdbType: String? = null,
    @SerializedName("user_rating")
    val userRating: Double? = null,
    @SerializedName("critic_score")
    val criticScore: Double? = null
)
