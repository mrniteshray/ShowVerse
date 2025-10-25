package xcom.niteshray.xapps.showverse.data.model

import com.google.gson.annotations.SerializedName

data class ContentResponse(
    @SerializedName("titles")
    val titles: List<ContentItem>
)
