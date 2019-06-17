package com.ysdc.comet.common.exception

import java.io.IOException

/**
 * Created by david on 14/3/18.
 */

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No connectivity exception"
}
