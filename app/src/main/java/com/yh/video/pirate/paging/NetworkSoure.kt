package com.yh.video.pirate.paging

import androidx.paging.PagingSource
import com.orhanobut.logger.Logger
import com.yh.video.pirate.constant.HttpConstant
import com.yh.video.pirate.repository.network.exception.CaomeiException
import com.yh.video.pirate.repository.network.exception.HttpError
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class NetworkSoure<T : Any>(
    val network: suspend (pageNum: Int, pageSize: Int) -> CaomeiResponse<CaomeiPaged<T>>,
    val filter: suspend (CaomeiResponse<CaomeiPaged<T>>) -> List<T>
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        // 每一页的数据长度
        val pageSize = params.loadSize
        return try {
            Logger.t("OkHttp").i("NetworkSoure")
            val invoke = network.invoke(page, pageSize)
            Logger.t("OkHttp").i("NetworkSoure -> end")
            if (invoke.code == HttpConstant.HTTP_SUCCESS) {
                //过滤数据源
                val list = filter.invoke(invoke)
                LoadResult.Page(
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

class NetworkSourePage<T : Any>(
    val network: suspend (pageNum: Int, pageSize: Int) -> CaomeiResponse<CaomeiPaged<T>>
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        // 每一页的数据长度
        val pageSize = params.loadSize
        return try {
            Logger.t("OkHttp").i("NetworkSourePage")
            val invoke = network.invoke(page, pageSize)
            Logger.t("OkHttp").i("NetworkSourePage -> end")
            if (invoke.code == HttpConstant.HTTP_SUCCESS) {
                //过滤数据源
//                delay(5000L)
                return LoadResult.Page(
                    data = invoke.rescont?.data ?: arrayListOf(),
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
class NetworkSoureSingleByFilter<T : Any>(
    val network: suspend () -> CaomeiResponse<List<T>>,
    val filter: suspend (CaomeiResponse<List<T>>) -> List<T>
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            //从网络获取数据
            Logger.t("OkHttp").i("NetworkSoureSingleByFilter")
            val invoke = network.invoke()
            Logger.t("OkHttp").i("NetworkSoureSingleByFilter -> end")
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

/**
 * 不分页
 */
class NetworkSoureSingle<T : Any>(val network: suspend () -> CaomeiResponse<List<T>>) :
    PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            //从网络获取数据
            Logger.t("OkHttp").i("NetworkSoureSingle")
            val invoke = network.invoke()
            Logger.t("OkHttp").i("NetworkSoureSingle -> end")
            if (invoke.code == HttpConstant.HTTP_SUCCESS) {
//                delay(3000L)
                return LoadResult.Page(
                    data = invoke.rescont ?: arrayListOf(),
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

/**
 * 不分页
 */
class NetworkSoureSingleByCaomeiPaged<T : Any>(val network: suspend () -> CaomeiResponse<CaomeiPaged<T>>) :
    PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            //从网络获取数据
            Logger.t("OkHttp").i("NetworkSoureSingleByCaomeiPaged")
            val invoke = network.invoke()
            Logger.t("OkHttp").i("NetworkSoureSingleByCaomeiPaged -> end")
            if (invoke.code == HttpConstant.HTTP_SUCCESS) {
//                delay(3000L)
                return LoadResult.Page(
                    data = invoke.rescont?.data ?: arrayListOf(),
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






