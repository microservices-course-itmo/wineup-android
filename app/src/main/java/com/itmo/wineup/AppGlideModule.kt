package com.itmo.wineup

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@GlideModule
class CustomAppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(getOkHttpClient()))
    }

    private fun getOkHttpClient(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            followRedirects(true)
            val sslContext = SSLContext.getInstance("SSL")
            val trustAllCerts = arrayOf(
                object : X509TrustManager {

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers() = emptyArray<X509Certificate>()

                }
            )
            sslContext.init(null, trustAllCerts, SecureRandom())
            sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            hostnameVerifier { _, _ -> true }

            build()
        }
    }

}