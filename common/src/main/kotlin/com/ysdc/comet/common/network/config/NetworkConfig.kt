package com.ysdc.comet.common.network.config

/**
 * Contains the configuration used for the networking
 */
interface NetworkConfig {

    /**
     * @return the base url for our network call
     */
    fun baseUrl(): String

    /**
     * return the username required for the basic network authentication
     */
    fun authUsername(): String

    /**
     * return the password required for the basic network authentication
     */
    fun authPassword(): String
}
