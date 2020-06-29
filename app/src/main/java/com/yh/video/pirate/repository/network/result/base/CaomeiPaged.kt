package com.yh.video.pirate.repository.network.result.base

import com.google.gson.annotations.SerializedName

data class CaomeiPaged<T>(val current_page: Int,val data: List<T>,val total: Int,val last_page:Int, val to:Int)