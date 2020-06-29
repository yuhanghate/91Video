package com.yh.video.pirate.paging

import androidx.paging.PagingSource
import com.yh.video.pirate.constant.HttpConstant
import com.yh.video.pirate.repository.network.exception.CaomeiException
import com.yh.video.pirate.repository.network.exception.HttpError
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse

class NetworkSoure<T:Any>(val network:  suspend (pageNum:Int, pageSize:Int)->CaomeiResponse<CaomeiPaged<T>>):PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 0
        // 每一页的数据长度
        val pageSize = params.loadSize
        return try {
            val invoke = network.invoke(page, pageSize)
            if (invoke.code == HttpConstant.HTTP_SUCCESS) {
                return LoadResult.Page(
                    data = invoke.rescont?.data ?: arrayListOf(),
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (invoke.rescont?.total!! - invoke.rescont.to > 0) null else page + 1
                )
            } else {
                LoadResult.Error(CaomeiException(HttpError.USER_EXIST))
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}




