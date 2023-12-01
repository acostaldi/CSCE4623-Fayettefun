package com.example.fayettefun.Util

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.fayettefun.R
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

class OpenStreetMapFragment : Fragment(), Marker.OnMarkerClickListener {

    private lateinit var mMap: MapView
    private lateinit var userMarker: Marker
    private lateinit var mapController: IMapController

    // Location variables
    private lateinit var mCurrentLocation: Location // Current location(latitude, longitude)
    private var centeredLocation: GeoPoint = GeoPoint(0.0, 0.0) // Location of camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        mMap = root.findViewById(R.id.map)
        userMarker = Marker(mMap) // Initializes user maker
        setupMapOptions() // Sets ups map options
        mapController = mMap.controller // Sets up map controller
        mapController.setZoom(15.0) // Adjusts map zoom
        return root
    }

    override fun onResume() {
        super.onResume()
        mMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMap.onPause()
    }

    private fun setupMapOptions() {
        mMap.isTilesScaledToDpi = true
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        addCopyrightOverlay()
        addRotationOverlay()

    }
    fun addUserMarker(location: Location){ // Used to update the location of the user marker real-time
        val userLocation = GeoPoint(location.latitude, location.longitude)
        userMarker.position = userLocation
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        userMarker.icon = ResourcesCompat.getDrawable(resources, R.drawable.user_pin, null)
        mMap.overlays.add(userMarker)
        Log.d("Marker", "Updating user marker location")
    }

    private fun addRotationOverlay() {
        val rotationGestureOverlay = RotationGestureOverlay(mMap)
        rotationGestureOverlay.isEnabled
        mMap.setMultiTouchControls(true)
        mMap.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)  // Makes zoom controls not be visible
        mMap.overlays.add(rotationGestureOverlay)
    }

    private fun addCopyrightOverlay() {
        val copyrightNotice: String = mMap.tileProvider.tileSource.copyrightNotice
        val copyrightOverlay = CopyrightOverlay(context)
        copyrightOverlay.setCopyrightNotice(copyrightNotice)
        mMap.overlays.add(copyrightOverlay)
    }

    private fun changeCenterLocation(geoPoint: GeoPoint) {
        centeredLocation = geoPoint
        mapController.setCenter(centeredLocation)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OpenStreetMapFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onMarkerClick(marker: Marker?, mapView: MapView?): Boolean {
        TODO("Not yet implemented")
    }

    fun updateCurrentLocation(location: Location) {
        mCurrentLocation = location  // Assigns the updated location to my current location variable
        centeredLocation = GeoPoint(location.latitude, location.longitude) // Transforms location to geopoint
        changeCenterLocation(centeredLocation) // Updates centered location
    }

}