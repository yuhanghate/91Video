package com.yh.video.pirate.ui.video.viewmodel

import com.dueeeke.videoplayer.player.VideoViewManager
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.result.VideoPlay
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.flow.Flow

class VideoPlayViewModel : BaseViewModel() {

    /**
     * 视频界面管理
     */
    fun getVideoViewManager() = VideoViewManager.instance()

    /**
     * 视频播放页面
     */
    fun getVideoPlay(id: Long): Flow<CaomeiResponse<VideoPlay>> {
        return mDataRepository.getVideoPlay(id)
    }
}