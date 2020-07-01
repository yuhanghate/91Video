package com.yh.video.pirate.repository.network.api

import com.yh.video.pirate.repository.network.result.CategoryResult
import com.yh.video.pirate.repository.network.result.MainResult
import com.yh.video.pirate.repository.network.result.RecommendedLikeResult
import com.yh.video.pirate.repository.network.result.VideoResult
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.FieldMap
import retrofit2.http.GET

interface NetApi {

    /**
     * 分类列表
     */
    @GET("/api/videosort")
    suspend fun getVideoSort(): CaomeiResponse<List<CategoryResult>>

    /**
     * 分类对应的视频列表
     */
    @GET("api/videoexplore")
    suspend fun getVideoList():CaomeiResponse<CaomeiPaged<VideoResult>>

    /**
     * 猜你喜欢
     */
    @GET("api/videomaylike")
    suspend fun getRecommendedLike(@FieldMap parsms:Map<String, Any>):Flow<CaomeiResponse<CaomeiPaged<RecommendedLikeResult>>>

    /**
     * 草莓主页列表
     */
    @GET("api/videoindex")
     fun getMainList():Flow<CaomeiResponse<List<MainResult>>>

    /**
     * 草莓主页列表
     */
    @GET("api/videoindex")
    suspend fun getMainList2():CaomeiResponse<List<MainResult>>
}