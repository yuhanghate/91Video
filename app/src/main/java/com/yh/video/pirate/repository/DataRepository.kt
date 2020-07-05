package com.yh.video.pirate.repository

import com.yh.video.pirate.repository.database.AppDatabase
import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity
import com.yh.video.pirate.repository.network.Http
import com.yh.video.pirate.repository.network.api.NetApi
import com.yh.video.pirate.repository.network.result.*
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.utils.application
import kotlinx.coroutines.flow.Flow
import java.util.*

object DataRepository {

    val mNetApi by lazy { Http.retrofit.create(NetApi::class.java) }

    val mDatabase by lazy { AppDatabase.getInstance(application) }

    /**
     * 分类列表
     */
    suspend fun getCategoryList(): CaomeiResponse<List<Category>> {
        return mNetApi.getVideoSort()
    }

    /**
     * 分类对应的视频列表
     */
    suspend fun getVideoList(
        pageNum: Int,
        categoryType: Int
    ): CaomeiResponse<CaomeiPaged<Discover>> {
        val map = mapOf("page" to pageNum, "per_page" to categoryType)
        return mNetApi.getVideoList(map)
    }

    /**
     * 猜你喜欢
     */
    suspend fun getRecommendedLike(): CaomeiResponse<CaomeiPaged<RecommendedLike>> {
        val map = mapOf("page" to 1)
        return mNetApi.getRecommendedLike(map)
    }


    /**
     * 草莓主页列表
     */
    suspend fun getMainList2(): CaomeiResponse<List<Main>> {
        return mNetApi.getMainList2()
    }

    /**
     * 视频类型:國產自拍/家庭亂倫
     */
    suspend fun getVideoType(): CaomeiResponse<List<VideoType>> {
        return mNetApi.getVideoType()
    }

    /**
     * 视频播放页面
     */
    fun getVideoPlay(id: Long): Flow<CaomeiResponse<Video>> {
        val map = mapOf<String, String>("uuid" to UUID.randomUUID().toString(), "device" to "0")
        return mNetApi.getVideoPlay(id, map)
    }

    /**
     * 历史记录
     */
    suspend fun getHistoryList(pageNum: Int): CaomeiResponse<CaomeiPaged<Video>> {
        val map = mapOf<String, String>(
            "page" to pageNum.toString(),
            "device" to "0",
            "uuid" to "31649453ca3bb198"
        )
        return mNetApi.getHistoryList(map)
    }

    /**
     * 搜索
     */
    suspend fun getSearch(pageNum: Int, keyword: String): CaomeiResponse<CaomeiPaged<Search>> {
        val map = mapOf<String, String>(
            "page" to pageNum.toString(),
            "serach" to keyword,
            "uuid" to "31649453ca3bb198",
            "device" to "0"
        )
        return mNetApi.getSearch(map)
    }

    /**
     * 获取搜索关键字
     */
    fun getSearchKeyword(): Flow<CaomeiResponse<List<SearchKeywords>>> {
        val map = mapOf<String, String>("uuid" to "31649453ca3bb198", "device" to "0")
        return mNetApi.getSearchKeyword(map)
    }

    /**
     * 获取本地搜索关键词
     */
    fun querySearchKeywords(): Flow<List<SearchHistoryEntity>> {
        return mDatabase.searchHistoryDao.query()
    }

    /**
     * 关键词保存本地
     */
    suspend fun insertSearchKeywords(keyword: String) {
        mDatabase.searchHistoryDao.insert(SearchHistoryEntity().apply {
            this.keyword = keyword
        })
    }

    /**
     * 清空本地记录
     */
    suspend fun cleanSearchkeywords() {
        mDatabase.searchHistoryDao.clear()
    }
}