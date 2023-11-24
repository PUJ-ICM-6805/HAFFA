package com.example.haffa.routes

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.haffa.R
import com.example.haffa.model.Route
import com.example.haffa.service.UserLocationService
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

class MapsFragment : Fragment() {

    private var route: Route? = null
    private var friendPhone: String? = null
    private var friendName: String? = null
    private lateinit var map: MapView
    private lateinit var mapController: IMapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        route = arguments?.getSerializable("route") as? Route
        friendPhone = arguments?.getString("PHONE")
        friendName = arguments?.getString("FRIEND_NAME")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map = view.findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        mapController = map.controller
        mapController.setZoom(18.0)

        if (route != null) {
            displayRoute()
        } else if (friendPhone != null) {
            displayFriendLocation()
        }
    }

    private fun displayFriendLocation() {
        val UserLocationService = UserLocationService()
        UserLocationService.getByPhone(friendPhone!!) { location ->
            map.overlays.clear()
            val geoPoint = GeoPoint(location.latitude, location.longitude)

            val marker = Marker(map)
            marker.position = geoPoint
            marker.icon = BitmapDrawable(
                resources,
                createStyledMarker(ContextCompat.getColor(requireContext(), R.color.red), 48)
            )
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            marker.title = friendName
            map.overlays.add(marker)

            mapController.setCenter(geoPoint)
        }
    }

    private fun displayRoute() {
        val geoPoints = route!!.locations.map {
            GeoPoint(it["latitude"]!!, it["longitude"]!!)
        }

        if (geoPoints.isEmpty()) return

        val polyline = Polyline(map)
        polyline.setPoints(geoPoints)
        polyline.color = ContextCompat.getColor(requireContext(), R.color.rose)
        polyline.width = 20f
        map.overlays.add(polyline)

        val startMarker = Marker(map)
        startMarker.position = geoPoints.first()
        startMarker.icon = BitmapDrawable(
            resources,
            createStyledMarker(ContextCompat.getColor(requireContext(), R.color.red), 48)
        )
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        map.overlays.add(startMarker)

        if (geoPoints.size > 1) {
            val endMarker = Marker(map)
            endMarker.position = geoPoints.last()
            endMarker.icon = BitmapDrawable(
                resources,
                createStyledMarker(ContextCompat.getColor(requireContext(), R.color.red), 48)
            )
            endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            map.overlays.add(endMarker)
        }

        mapController.setCenter(geoPoints.first())
    }

    private fun createStyledMarker(color: Int, diameter: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val outerCirclePaint = Paint().apply {
            this.color = color
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val innerCirclePaint = Paint().apply {
            this.color = ContextCompat.getColor(requireContext(), R.color.white)
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val centerDotPaint = Paint().apply {
            this.color = color
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        val outerRadius = diameter / 2f
        val innerRadius = outerRadius * 0.7f
        val centerDotRadius = innerRadius * 0.3f

        canvas.drawCircle(outerRadius, outerRadius, outerRadius, outerCirclePaint)
        canvas.drawCircle(outerRadius, outerRadius, innerRadius, innerCirclePaint)
        canvas.drawCircle(outerRadius, outerRadius, centerDotRadius, centerDotPaint)

        return bitmap
    }
}
