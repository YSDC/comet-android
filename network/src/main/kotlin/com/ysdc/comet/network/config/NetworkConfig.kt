package com.ysdc.comet.network.config

/**
 * Contains the configuration used for the networking
 */
interface NetworkConfig {

    /**
     * @return the base url for our network call
     */
    fun baseUrl(): String

    /**
     * return the secret used to connect to the swiss league
     */
    fun secret(): String

    /**
     * return the api key used to connect to the swiss league
     */
    fun apiKey(): String
}
