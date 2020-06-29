package com.yh.video.pirate.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.exception.convertHttpRes
import com.yh.video.pirate.repository.network.exception.handlingApiExceptions
import com.yh.video.pirate.repository.network.exception.handlingExceptions
import com.yh.video.pirate.repository.network.exception.handlingHttpResponse
import com.yh.video.pirate.repository.network.result.CategoryResult
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.utils.pager
import kotlinx.coroutines.flow.flowOn

class MainViewModel : BaseViewModel() {
    val category: MutableLiveData<List<CategoryResult>> = MutableLiveData()

    val pager by lazy {
        pager { pageNum, _ -> mDataRepository.getVideoList(pageNum, 3) }
    }

    /**
     * 获取分类列表
     */
    suspend fun doCategoryList() {
        launchOnIO(
            tryBlock = {
                mDataRepository.getCategoryList().run {
                    handlingHttpResponse<CaomeiResponse<List<CategoryResult>>>(
                        convertHttpRes(),
                        successBlock = { category.postValue(this.rescont) },
                        failureBlock = { error -> handlingApiExceptions(error) }
                    )
                }
            },
            catchBlock = { e -> handlingExceptions(e) }
        )
    }
}