package com.yh.video.pirate.ui.main.viewmodel

import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.result.Video
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.ui.main.adapter.DiscoverAdapter
import com.yh.video.pirate.utils.loadStateAdapter
import com.yh.video.pirate.utils.pager

class DiscoverViewModel : BaseViewModel() {

    val adapter by lazy { DiscoverAdapter().loadStateAdapter() }

    /**
     * 获取列表数据
     */
    val videoList by lazy {
        pager(callback = { pageNum, _ -> mDataRepository.getVideoList(pageNum, 3) },
            filter = { getFilterList(it) })
    }

    /**
     * 过滤草莓广告
     */
    private fun getFilterList(data: CaomeiResponse<CaomeiPaged<Video>>): List<Video> {
        data.rescont ?: return emptyList()
        return data.rescont.data.filter { it.is_ad != 1 }.map { it }.toList()
    }
}