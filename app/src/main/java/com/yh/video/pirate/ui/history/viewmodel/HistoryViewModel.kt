package com.yh.video.pirate.ui.history.viewmodel

import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.ui.history.adapter.HistoryAdapter
import com.yh.video.pirate.utils.loadStateAdapter
import com.yh.video.pirate.utils.pager

class HistoryViewModel : BaseViewModel() {

    val adapter by lazy { HistoryAdapter().loadStateAdapter() }

    /**
     * 获取历史列表
     */
    val getHistoryList = pager(
        callback = { pageNum, _ -> mDataRepository.getHistoryList(pageNum) }
    )
}