package com.yh.video.pirate.repository.mapper

import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity
import com.yh.video.pirate.repository.network.result.SearchKeywords

class SearchKeywords2SearchHistoryEntity(val type:String) : Mapper<SearchKeywords, List<SearchHistoryEntity>> {
    override fun map(input: SearchKeywords): List<SearchHistoryEntity> {
        val t = type
        return input.lists?.map { title ->
            SearchHistoryEntity().apply {
                type = t
                keyword = title
                updateTime = System.currentTimeMillis() / 1000
            }
        }?.toList()?: arrayListOf()
    }
}