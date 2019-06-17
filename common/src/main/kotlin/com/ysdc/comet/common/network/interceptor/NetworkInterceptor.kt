package com.ysdc.comet.common.network.interceptor

import com.ysdc.comet.common.exception.NetworkError
import com.ysdc.comet.common.utils.AppConstants
import com.ysdc.comet.common.utils.AppConstants.LOGGING_CATEGORY_NETWORK
import com.ysdc.comet.common.utils.extension.removeSensitiveValues
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 * Utility Interceptor to log last Internet Call and append it to CrashReport on Firebase when a exception occurs
 */
class NetworkInterceptor(private val crashlyticsUtils: com.ysdc.comet.common.utils.CrashlyticsUtils) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val utf8 = Charset.forName("UTF-8")

        val request = chain.request()
        val requestBody = request.body()

        // Headers
        var headersString = AppConstants.EMPTY_STRING
        val headers = request.headers()
        for (i in 0 until headers.size()) {
            val name = headers.name(i)

            if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                headersString.plus(name + ": " + headers.value(i) + "; ")
            }
        }

        // Request Body
        var bodyString = AppConstants.EMPTY_STRING
        if (requestBody != null && !bodyEncoded(request.headers())) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            val charset: Charset = requestBody.contentType()?.charset(utf8) ?: utf8
            bodyString += if (isPlaintext(buffer)) {
                buffer.readString(charset)
            } else {
                "--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)"
            }
        }


        val response = chain.proceed(request)

        var responseBodyString = AppConstants.EMPTY_STRING
        // Response Body
        if (HttpHeaders.hasBody(response)) {
            val responseBody = response.body()
            val source = responseBody!!.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            val charset: Charset = responseBody.contentType()?.charset(utf8) ?: utf8
            if (!isPlaintext(buffer)) {
                responseBodyString += "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)"
            }

            if (responseBody.contentLength() != 0L) {
                responseBodyString += buffer.clone().readString(charset)
            }
        }


        // Log Http Not success calls
        if (response.code() !in 200..299) {
            crashlyticsUtils.setKeyString("httpMethod", request.method())
            crashlyticsUtils.setKeyString("error-code", response.code().toString())
            crashlyticsUtils.setKeyString("error-domain", request.method() + ' '.toString() + request.url())
            crashlyticsUtils.setKeyString("parameters", bodyString.removeSensitiveValues(Arrays.asList("password")))
            crashlyticsUtils.setKeyString("requestHeader", headersString)
            crashlyticsUtils.setKeyString("responseData", responseBodyString)
            crashlyticsUtils.setKeyString("responseResult", "FAILURE")
            crashlyticsUtils.setKeyString("url", request.url().toString())

            crashlyticsUtils.logException(
                NetworkError(
                    String.format(
                        "%s %s",
                        request.method(),
                        request.url().toString()
                    )
                )
            )
        } else {
            crashlyticsUtils.log(
                LOGGING_CATEGORY_NETWORK,
                request.method(),
                request.url().toString(),
                response.code().toLong()
            )
        }

        return response

    }
}

private fun bodyEncoded(headers: Headers): Boolean {
    val contentEncoding = headers.get("Content-Encoding")
    return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
}

private fun isPlaintext(buffer: Buffer): Boolean {
    try {
        val prefix = Buffer()
        val byteCount = if (buffer.size() < 64) buffer.size() else 64
        buffer.copyTo(prefix, 0, byteCount)
        for (i in 0..15) {
            if (prefix.exhausted()) {
                break
            }
            val codePoint = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
        }
        return true
    } catch (e: EOFException) {
        return false // Truncated UTF-8 sequence.
    }

}
