package com.yh.video.pirate.repository.network.api

import com.yh.video.pirate.repository.network.result.*
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface NetApi {

    /**
     * 视频类型:國產自拍/家庭亂倫
     */
    @GET("api/videosort")
    suspend fun getVideoType(): CaomeiResponse<List<VideoType>>

    /**
     * 分类列表
     */
    @GET("api/videosort")
    suspend fun getVideoSort(): CaomeiResponse<List<Category>>

    /**
     * 分类对应的视频列表
     */
    @GET("api/videoexplore")
    suspend fun getVideoList(@QueryMap parsms: Map<String, Int>): CaomeiResponse<CaomeiPaged<Video>>

    /**
     * 猜你喜欢
     */
    @GET("api/videomaylike")
    suspend fun getRecommendedLike(@QueryMap parsms: Map<String, Any>): @JvmSuppressWildcards Flow<CaomeiResponse<CaomeiPaged<RecommendedLike>>>

    /**
     * 草莓主页列表
     */
    @GET("api/videoindex")
    suspend fun getMainList2(): CaomeiResponse<List<Main>>

    /**
     * 视频播放页面
     */
    @GET("api/videoplay/{id}")
    fun getVideoPlay(
        @Path("id") id: Long,
        @QueryMap parsms: Map<String, String>
    ): Flow<CaomeiResponse<VideoPlay>>
}