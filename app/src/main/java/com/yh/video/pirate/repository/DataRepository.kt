package com.yh.video.pirate.repository

import androidx.lifecycle.asLiveData
import com.yh.video.pirate.repository.database.AppDatabase
import com.yh.video.pirate.repository.network.Http
import com.yh.video.pirate.repository.network.api.NetApi
import com.yh.video.pirate.repository.network.result.CategoryResult
import com.yh.video.pirate.repository.network.result.MainResult
import com.yh.video.pirate.repository.network.result.RecommendedLikeResult
import com.yh.video.pirate.repository.network.result.VideoResult
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.utils.application
import kotlinx.coroutines.flow.Flow

object DataRepository {

    val mNetApi by lazy { Http.retrofit.create(NetApi::class.java) }

    val mDatabase by lazy { AppDatabase.getInstance(application) }

    /**
     * 分类列表
     */
    suspend fun getCategoryList(): CaomeiResponse<List<CategoryResult>> {
        return mNetApi.getVideoSort()
    }

    suspend fun getVideoList(page:Int, categoryType:Int): CaomeiResponse<CaomeiPaged<VideoResult>> {
        val map = mapOf<String, Int>("page" to page, "per_page" to categoryType)
        return mNetApi.getVideoList()
    }

    /**
     * 猜你喜欢
     */
    suspend fun getRecommendedLike(page: Int): Flow<CaomeiResponse<CaomeiPaged<RecommendedLikeResult>>> {
        val map = mapOf("page" to page)
        return mNetApi.getRecommendedLike(map)
    }

    /**
     * 草莓主页列表
     */
      fun getMainList(): Flow<CaomeiResponse<List<MainResult>>> {
        return mNetApi.getMainList()
    }

    /**
     * 草莓主页列表
     */
    suspend fun getMainList2(): CaomeiResponse<List<MainResult>> {
        return mNetApi.getMainList2()
    }
}