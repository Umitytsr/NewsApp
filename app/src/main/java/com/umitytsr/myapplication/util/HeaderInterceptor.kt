package com.umitytsr.myapplication.util

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(Constants.AUTHORIZATION,Constants.API_KEY_1)
            .build()
        return chain.proceed(request)
    }
}