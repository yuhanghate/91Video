package com.yh.video.pirate.repository.network.result.base

/* 服务器返回数剧 */
data class CaomeiResponse<out T>(val code: Int, /*val errorMsg: String?,*/ val rescont: T?, val msg: String)