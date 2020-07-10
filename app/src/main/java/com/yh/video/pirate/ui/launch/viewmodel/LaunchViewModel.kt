package com.yh.video.pirate.ui.launch.viewmodel

import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity
import com.yh.video.pirate.repository.mapper.SearchKeywords2SearchHistoryEntity
import com.yh.video.pirate.repository.network.result.SearchKeywords
import com.yh.video.pirate.repository.network.result.VideoType
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.flow.Flow

class LaunchViewModel : BaseViewModel() {
    /**
     * 获取搜索关键字
     */
    suspend fun getSearchKeyword(): Flow<CaomeiResponse<List<SearchKeywords>>> {
        return mDataRepository.getSearchKeyword()
    }

    /**
     * 保存热闹搜索
     */
    suspend fun insertSearchHot(keywords: SearchKeywords?) {
        keywords ?: return
        val list = SearchKeywords2SearchHistoryEntity(SearchHistoryEntity.TYPE_HOT).map(keywords)
        mDataRepository.insertSearchHot(list)
    }

    /**
     * 保存推荐搜索
     */
    suspend fun insertSearchRecomment(keywords: SearchKeywords?) {
        keywords ?: return
        val list = SearchKeywords2SearchHistoryEntity(SearchHistoryEntity.TYPE_RECOMMENT).map(keywords)
        mDataRepository.insertSearchRecomment(list)
    }
}