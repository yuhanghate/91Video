package com.yh.video.pirate.ui.search.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity
import com.yh.video.pirate.repository.network.result.Search
import com.yh.video.pirate.repository.network.result.SearchKeywords
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.ui.search.adapter.SearchAdapter
import com.yh.video.pirate.utils.pager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel:BaseViewModel() {

    val adapter by lazy { SearchAdapter() }

    /**
     * 点击联想功能以后关键字
     */
    var searchSuggestStr = ""


    /**
     * 获取搜索关键字
     */
    fun getSearchKeyword(): Flow<CaomeiResponse<List<SearchKeywords>>> {
        return mDataRepository.getSearchKeyword()
    }

    /**
     * 搜索关键字
     */
    suspend fun getSearch(keyword: String):Flow<PagingData<Search>> {
        return pager { pageNum, _ ->
            mDataRepository.getSearch(pageNum, keyword)
        }
    }

    /**
     * 获取本地搜索关键词
     */
    suspend fun querySearchKeywords(): Flow<List<SearchHistoryEntity>> {
        return mDataRepository.querySearchKeywords()
    }

    /**
     * 关键词保存本地
     */
     fun insertSearchKeywords(keyword: String) {
        if (keyword.isEmpty()) return
        viewModelScope.launch {
            mDataRepository.insertSearchKeywords(keyword)
        }
    }

    /**
     * 清空本地记录
     */
    fun cleanSearchkeywords() {
        viewModelScope.launch {
            mDataRepository.cleanSearchkeywords()
        }
    }
}