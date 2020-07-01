package com.yh.video.pirate.paging

import androidx.paging.PagingSource
import com.yh.video.pirate.constant.HttpConstant
import com.yh.video.pirate.repository.network.exception.CaomeiException
import com.yh.video.pirate.repository.network.exception.HttpError
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.delay

class NetworkSoure<T:Any>(val network:  suspend (pageNum:Int, pageSize:Int)->CaomeiResponse<CaomeiPaged<T>>,
                          val filter:suspend (CaomeiResponse<CaomeiPaged<T>>)->List<T>):PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        // 每一页的数据长度
        val pageSize = params.loadSize
        return try {
            val invoke = network.invoke(page, pageSize)
            if (invoke.code == HttpConstant.HTTP_SUCCESS) {
                //过滤数据源
                val list = filter.invoke(invoke)
//                delay(5000L)
                return LoadResult.Page(
                    data = list,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (invoke.rescont?.total!! - invoke.rescont.to > 0) page + 1 else null
                )
            } else {
                LoadResult.Error(CaomeiException(HttpError.USER_EXIST))
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

/**
 * 不分页
 */
class NetworkSoureSingle<T:Any>(val network:  suspend ()->CaomeiResponse<List<T>>, val filter:suspend (CaomeiResponse<List<T>>)->List<T>):PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            //从网络获取数据
            val invoke = network.invoke()
            if (invoke.code == HttpConstant.HTTP_SUCCESS) {
                //过滤数据源
                val list = filter.invoke(invoke)
//                delay(3000L)
                return LoadResult.Page(
                    data = list,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = null
                )
            } else {
                LoadResult.Error(CaomeiException(HttpError.USER_EXIST))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}




