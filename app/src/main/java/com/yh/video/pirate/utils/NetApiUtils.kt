package com.yh.video.pirate.utils

import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <T> CaomeiResponse<T>.asFlow():Flow<CaomeiResponse<T>> {
    return flowOf(this)
}