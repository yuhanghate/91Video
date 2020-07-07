package com.yh.video.pirate.ui.launch.viewmodel

import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.result.VideoType
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse

class LaunchViewModel:BaseViewModel() {

    suspend fun getVideoType(): CaomeiResponse<List<VideoType>> {
        return mDataRepository.getVideoType()
    }
}