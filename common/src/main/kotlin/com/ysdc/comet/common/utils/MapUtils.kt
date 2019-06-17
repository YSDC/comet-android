package com.ysdc.comet.common.utils

import ae.propertyfinder.model.Coordinates

class MapUtils {

    fun convertGeoHash(stringHash: String): ArrayList<Coordinates> {
        val geoHash = GeoHash(stringHash)
        return arrayListOf(
            Coordinates(geoHash.boundingBox.topLeft.latitude, geoHash.boundingBox.topLeft.longitude),
            Coordinates(geoHash.boundingBox.topRight.latitude, geoHash.boundingBox.topRight.longitude),
            Coordinates(geoHash.boundingBox.bottomRight.latitude, geoHash.boundingBox.bottomRight.longitude),
            Coordinates(geoHash.boundingBox.bottomLeft.latitude, geoHash.boundingBox.bottomLeft.longitude)
        )
    }
}