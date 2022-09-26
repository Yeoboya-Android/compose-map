package kr.co.inforexseoul.compose_map.map.google

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import kr.co.inforexseoul.compose_map.R


class MarkerClusterRenderer(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<ClusterData>?
) : DefaultClusterRenderer<ClusterData>(
        context,
        map,
        clusterManager
    ) {
    private val iconGenerator: IconGenerator
    private val markerImageView: ImageView
    override fun onBeforeClusterItemRendered(
        item: ClusterData,
        markerOptions: MarkerOptions
    ) {
        markerImageView.setImageResource(R.drawable.bus_station)
        val icon: Bitmap = iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
        markerOptions.title(item.title)
    }

    companion object {
        private const val MARKER_DIMENSION = 48
    }

    init {
        iconGenerator = IconGenerator(context)
        markerImageView = ImageView(context)
        markerImageView.layoutParams = ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION)
        iconGenerator.setContentView(markerImageView)
    }
}