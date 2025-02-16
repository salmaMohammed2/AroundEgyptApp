package com.example.aroundegypt.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the original request
        val originalRequest = chain.request()

        // Build the new request with the API key added as a query parameter
        val url = originalRequest.url.newBuilder()
            .build()

        // Create a new request with the modified URL
        val newRequest = originalRequest.newBuilder().url(url).build()

        // Proceed with the new request
        return chain.proceed(newRequest)
    }
}