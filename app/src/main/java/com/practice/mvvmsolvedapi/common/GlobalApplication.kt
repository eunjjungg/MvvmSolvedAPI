package com.practice.mvvmsolvedapi.common

import android.app.Application
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.practice.mvvmsolvedapi.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class GlobalApplication: Application() {
    companion object {
        // var prefs: MySharedPreferences

        //Retrofit2
        lateinit var baseService: Retrofit
            private set

        //Glide URL -> ImageView
        @BindingAdapter("imageFromUrl")
        @JvmStatic
        fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
            if(!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
            } else {
                view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_no_image))
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        //prefs = MySharedPreferences(applicationContext)

        baseService = initRetrofitBuilder()
    }

    private fun initRetrofitBuilder(): Retrofit {
        val BASE_URL = "https://solved.ac"

        val okHttpClient = getUnsafeOkHttpClient() // SSL인증서 허용을 위한 OkHttpClient.Builder
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            //.writeTimeout(20, TimeUnit.SECONDS) //쓰는 기능은 현재 프로젝트에 없음.
            //.addNetworkInterceptor(InterceptorForHeader()) 헤더가 필요 없음.
            .build()

        //리턴하는 레트로핏
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        return builder
    }
}