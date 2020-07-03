package com.yh.video.pirate.repository.network.api

import com.yh.video.pirate.repository.network.result.*
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetApi {

    /**
     * 视频类型:國產自拍/家庭亂倫
     */
    @GET("api/videosort")
    suspend fun getVideoType():CaomeiResponse<List<VideoTypeResult>>

    /**
     * 分类列表
     */
    @GET("api/videosort")
    suspend fun getVideoSort(): CaomeiResponse<List<CategoryResult>>

    /**
     * 分类对应的视频列表
     */
    @GET("api/videoexplore")
    suspend fun getVideoList(@QueryMap parsms: Map<String, Int>): CaomeiResponse<CaomeiPaged<VideoResult>>

    /**
     * 猜你喜欢
     */
    @GET("api/videomaylike")
    suspend fun getRecommendedLike(@QueryMap parsms: Map<String, Any>):@JvmSuppressWildcards Flow<CaomeiResponse<CaomeiPaged<RecommendedLikeResult>>>

    /**
     * 草莓主页列表
     */
    @GET("api/videoindex")
    suspend fun getMainList2(): CaomeiResponse<List<MainResult>>
}