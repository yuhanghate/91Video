package com.yh.video.pirate.ui.main.viewmodel

import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.mapper.Any2MainResult
import com.yh.video.pirate.repository.network.result.Main
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.ui.main.adapter.MainAdapter
import com.yh.video.pirate.ui.main.fragment.CategoryFragment
import com.yh.video.pirate.ui.main.fragment.DiscoverFragment
import com.yh.video.pirate.ui.main.fragment.MainFragment
import com.yh.video.pirate.ui.main.fragment.MeFragment
import com.yh.video.pirate.utils.loadStateAdapter
import com.yh.video.pirate.utils.pagerSingle

class MainViewModel : BaseViewModel() {

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

    val mMainList = pagerSingle(
        callback = {
            mDataRepository.getMainList2()
        },
        filter = {
                getFilterList(it)
        })


    /**
     * 过滤草莓广告
     */
    fun getFilterList(data: CaomeiResponse<List<Main>>): List<Main> {
        data.rescont ?: return emptyList()

        val list = arrayListOf<Main>()
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