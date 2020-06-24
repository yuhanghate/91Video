package com.yh.video.pirate.repository.network.api

import com.yh.video.pirate.repository.network.result.VideoSortResult
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import retrofit2.http.GET

interface NetApi {

    /**
     * 分类列表
     */
    @GET("/api/videosort")
    suspend fun getVideoSort(): CaomeiResponse<List<VideoSortResult>>
}