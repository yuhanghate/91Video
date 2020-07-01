package com.yh.video.pirate.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.base.LoadStateAdapter
import com.yh.video.pirate.repository.mapper.Any2MainResult
import com.yh.video.pirate.repository.network.exception.handlingApiExceptions
import com.yh.video.pirate.repository.network.exception.handlingExceptions
import com.yh.video.pirate.repository.network.exception.catchCode
import com.yh.video.pirate.repository.network.result.CategoryResult
import com.yh.video.pirate.repository.network.result.MainResult
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.ui.main.adapter.MainAdapter
import com.yh.video.pirate.ui.main.fragment.CategoryFragment
import com.yh.video.pirate.ui.main.fragment.DiscoverFragment
import com.yh.video.pirate.ui.main.fragment.MainFragment
import com.yh.video.pirate.ui.main.fragment.MeFragment
import com.yh.video.pirate.utils.*
import kotlinx.coroutines.flow.Flow

class MainViewModel2 : BaseViewModel() {

    //标题
    val titles by lazy { arrayListOf<String>("主页", "分类", "发现", "我的") }

    //未选Tab图标
    val tabNormals by lazy {
        listOf(
            R.drawable.ic_tab_main_normal,
            R.drawable.ic_tab_category_normal,
            R.drawable.ic_tab_discover_normal,
            R.drawable.ic_tab_me_normal
        )
    }

    //选中Tab图标
    val tabPresseds by lazy {
        listOf(
            R.drawable.ic_tab_main_pressed,
            R.drawable.ic_tab_category_pressed,
            R.drawable.ic_tab_discover_pressed,
            R.drawable.ic_tab_me_pressed
        )
    }

    //Fragment
    val fragments by lazy {
        arrayListOf(
            MainFragment.newInstance(),
            CategoryFragment.newInstance(),
            DiscoverFragment.newInstance(),
            MeFragment.newInstance()
        )
    }

    //主页适配器
    val adapter by lazy { MainAdapter().loadStateAdapter() }

    val pager by lazy {
        pager { pageNum, _ -> mDataRepository.getVideoList(pageNum, 3) }
    }

    val mMainList = pagerSingle(
        callback = {
            mDataRepository.getMainList2()
        },
        filter = {
                getFilterList(it)
//            it.rescont?: arrayListOf()
        })

    /**
     * 首页列表
     */
    fun getMainList(): Flow<CaomeiResponse<List<MainResult>>> {
        return mDataRepository.getMainList()
    }

    /**
     * 过滤草莓广告
     */
    fun getFilterList(data: CaomeiResponse<List<MainResult>>): List<MainResult> {
        data.rescont ?: return emptyList()

        val list = arrayListOf<MainResult>()
        data.rescont.filter { it.is_ad != 1 }.forEach { it ->
            //添加标题
            list.add(it)
            //添加内容列表
            it.list?.filter { it.is_ad != 1 }?.map { it }?.toList()
                ?.run { list.addAll(this) }
            //添加分隔线
            list.add(Any2MainResult().map(Any()))
            it.list = null
        }
        return list
    }
}