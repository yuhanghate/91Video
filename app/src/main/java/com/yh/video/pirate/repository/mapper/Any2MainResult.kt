package com.yh.video.pirate.repository.mapper

import com.yh.video.pirate.repository.network.result.MainResult

class Any2MainResult : Mapper<Any, MainResult> {
    override fun map(input: Any): MainResult {
        return MainResult(
            id = null,
            title = null,
            coverpath = null,
            is_ad = null,
            list = null,
            name = null,
            type = null,
            type_id = null
        )
    }
}