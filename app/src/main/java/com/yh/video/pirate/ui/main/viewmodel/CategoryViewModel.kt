package com.yh.video.pirate.ui.main.viewmodel

import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.result.VideoType
import com.yh.video.pirate.ui.main.adapter.CategoryAdapter
import com.yh.video.pirate.utils.pagerSingleByList

class CategoryViewModel : BaseViewModel() {

    //Fragment
    val fragments: ArrayList<BaseFragment<*, *>> = arrayListOf()



    val adapter by lazy { CategoryAdapter() }

    /**
     * 视频分类
     */
    val mVideoType = pagerSingleByList(
        callback = {
            mDataRepository.getVideoType()
        },
        filter = {
            val list = arrayListOf<VideoType>()
            list.add(VideoType(id = 0, name = "今日最新"))
            it.rescont?.let {it -> list.addAll(it) }
            list
        }
    )

}