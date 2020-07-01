package com.yh.video.pirate.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.yh.video.pirate.paging.NetworkSoure
import com.yh.video.pirate.paging.NetworkSoureSingle
import com.yh.video.pirate.repository.network.Http
import com.yh.video.pirate.repository.network.result.base.CaomeiPaged
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse

 fun <T:Any> ViewModel.pager(callback:  suspend (pageNum:Int, pageSize:Int)-> CaomeiResponse<CaomeiPaged<T>>) = Pager(
    config = Http.pagingConfig,
    pagingSourceFactory = {
        return@Pager NetworkSoure(network = callback)
    }).flow.cachedIn(viewModelScope)


fun <T:Any> ViewModel.pagerSingle(callback:  suspend ()-> CaomeiResponse<List<T>>,  filter:suspend (CaomeiResponse<List<T>>)->List<T>) = Pager(
    config = Http.pagingConfig,
    pagingSourceFactory = {
        return@Pager NetworkSoureSingle(network = callback, filter = filter)
    }).flow.cachedIn(viewModelScope)