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
     * 分类对应的视频列表
     */
    @GET("api/videoexplore")
    suspend fun getVideoList(@QueryMap parsms: Map<String, Int>): CaomeiResponse<CaomeiPaged<Discover>>

    /**
     * 猜你喜欢
     */
    @GET("api/videomaylike")
    suspend fun getRecommendedLike(@QueryMap parsms: Map<String, Int>): @JvmSuppressWildcards CaomeiResponse<CaomeiPaged<RecommendedLike>>

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
        @Path("id") id: Long, @QueryMap parsms: Map<String, String>
    ): Flow<CaomeiResponse<Video>>

    /**
     * 历史记录
     */
    @GET("api/userseen")
    suspend fun getHistoryList(@QueryMap parsms: Map<String, String>):CaomeiResponse<CaomeiPaged<Video>>

    /**
     * 获取搜索关键字
     */
    @GET("api/videoSearchHot")
     fun getSearchKeyword(@QueryMap parsms: Map<String, String>):Flow<CaomeiResponse<List<SearchKeywords>>>

    /**
     * 搜索
     */
    @GET("api/videosort/0")
    suspend fun getSearch(@QueryMap parsms: Map<String, String>):CaomeiResponse<CaomeiPaged<Search>>


    /**
     * 分类列表
     */
    @GET("api/videosort/{id}}")
    suspend fun getCategoryList(@Path("id") id:Int, @QueryMap parsms: Map<String, String>):CaomeiResponse<CaomeiPaged<CategoryList>>
}