package kr.co.inforexseoul.compose_map.map.google

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ClusterData(
    val itemPosition : LatLng,
    val itemTitle : String,
    val itemSnippet : String
) : ClusterItem {
    override fun getPosition(): LatLng = itemPosition

    override fun getTitle(): String = itemTitle

    override fun getSnippet(): String = itemSnippet
}