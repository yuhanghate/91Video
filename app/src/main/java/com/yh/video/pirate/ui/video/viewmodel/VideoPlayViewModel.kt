package com.yh.video.pirate.ui.video.viewmodel

import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.result.Video
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.ui.video.adapter.RecommendLikeAdapter
import com.yh.video.pirate.utils.pagerSingleByCaomeiPaged
import kotlinx.coroutines.flow.Flow

class VideoPlayViewModel : BaseViewModel() {

    val adapter by lazy { RecommendLikeAdapter() }

    /**
     * 猜你喜欢
     */
    val getRecommendedLike = pagerSingleByCaomeiPaged(callback = {
         mDataRepository.getRecommendedLike()
    })


    /**
     * 视频播放页面
     */
    fun getVideoPlay(id: Long): Flow<CaomeiResponse<Video>> {
        return mDataRepository.getVideoPlay(id)
    }

}