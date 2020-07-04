package com.yh.video.pirate.repository.mapper

import com.yh.video.pirate.repository.network.result.Main

class Any2MainResult : Mapper<Any, Main> {
    override fun map(input: Any): Main {
        return Main(
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