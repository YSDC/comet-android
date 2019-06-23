package com.ysdc.comet.common.exception

import java.io.IOException

class OperationCanceledException : IOException() {
    override val message: String?
        get() = "Operation was canceled"
}