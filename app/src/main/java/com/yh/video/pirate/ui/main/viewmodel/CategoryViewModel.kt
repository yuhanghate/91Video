package com.yh.video.pirate.ui.main.viewmodel

import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.result.VideoType
import com.yh.video.pirate.ui.main.adapter.CategoryAdapter
import com.yh.video.pirate.ui.main.fragment.VideoListFragment
import com.yh.video.pirate.utils.pagerSingle

class CategoryViewModel : BaseViewModel() {

    //Fragment
    val fragments: ArrayList<BaseFragment<*, *>> = arrayListOf()

    /**
     * 初始化所有类型Fragment
     */
    fun initFragments(list: List<VideoType>) {
        fragments.addAll(list.filter { it.id != null }
            .map { VideoListFragment.newInstance(it.id!!) }.toList())
    }


    val adapter by lazy { CategoryAdapter() }

    /**
     * 视频分类
     */
    val mVideoType = pagerSingle(
        callback = {
            mDataRepository.getVideoType()
        }
    )

//    /**
//     * 视频类型:國產自拍/家庭亂倫
//     */
//    suspend fun getVideoType(): CaomeiResponse<List<VideoTypeResult>> {
//        return mDataRepository.getVideoType()
//    }
}