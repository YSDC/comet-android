package com.ysdc.comet.common.ui.utils

import com.ysdc.comet.common.R
import com.ysdc.comet.common.application.GeneralConfig
import ae.propertyfinder.model.ProjectDimension
import com.ysdc.comet.common.di.module.GlideApp
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import okhttp3.Credentials
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions


class GlideUtils(
    private val networkConfig: com.ysdc.comet.common.network.config.NetworkConfig,
    private val generalConfig: GeneralConfig
) {

    fun loadImage(imageView: ImageView, url: String?, widthHeightPair: Pair<Int, Int>, roundedCorner: Int = 0) {

        var requestOptions = RequestOptions()
        if (roundedCorner > 0) {
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(roundedCorner))
        }

        if (url != null) {
            val glideUrl = if (generalConfig.dimension() != ProjectDimension.PRODUCTION) {
                GlideUrl(
                    url,
                    LazyHeaders.Builder()
                        .addHeader(
                            "Authorization",
                            Credentials.basic(networkConfig.authUsername(), networkConfig.authPassword())
                        )
                        .build()
                )
            } else {
                GlideUrl(url)
            }
            GlideApp.with(imageView.context)
                .load(glideUrl)
                .centerCrop()
                .apply(requestOptions)
                .placeholder(R.drawable.ic_property_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        } else {
            GlideApp.with(imageView.context)
                .load(R.drawable.ic_no_image_placeholder)
                .override(widthHeightPair.first, widthHeightPair.second)
                .apply(requestOptions)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }
}