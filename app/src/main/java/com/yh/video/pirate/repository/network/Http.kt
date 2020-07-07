package com.yh.video.pirate.repository.network

import androidx.paging.PagingConfig
import com.yh.video.pirate.repository.network.adapter.FlowCallAdapterFactory
import com.yh.video.pirate.utils.gson
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory


object Http : AbstractHttp() {

    override val baseUrl: String = "http://cmapi.caomeisp777.com/"

    override val header: Map<String, String>
        get() = mapOf("Accept-Language" to "zh-cn,zh;q=0.8")

    override val convertersFactories: Iterable<Converter.Factory> = listOf(
        GsonConverterFactory.create()
    )

    override val callAdapterFactories: Iterable<CallAdapter.Factory>
        get() = listOf(
            FlowCallAdapterFactory()
        )

    override val interceptors: Iterable<Interceptor>
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return arrayListOf(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
        }

    public override val pagingConfig: PagingConfig
        get() = super.pagingConfig

    /**
     * 不预加载
     */
    public val pagingConfigOnePage: PagingConfig = PagingConfig(
        // 每页显示的数据的大小
        pageSize = 20
    )
}