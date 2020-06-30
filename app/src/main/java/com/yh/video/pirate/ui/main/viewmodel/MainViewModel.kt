package com.yh.video.pirate.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.exception.handlingApiExceptions
import com.yh.video.pirate.repository.network.exception.handlingExceptions
import com.yh.video.pirate.repository.network.exception.catchCode
import com.yh.video.pirate.repository.network.result.CategoryResult
import com.yh.video.pirate.repository.network.result.MainResult
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.ui.main.fragment.CategoryFragment
import com.yh.video.pirate.ui.main.fragment.DiscoverFragment
import com.yh.video.pirate.ui.main.fragment.MainFragment
import com.yh.video.pirate.ui.main.fragment.MeFragment
import com.yh.video.pirate.utils.pager
import kotlinx.coroutines.flow.Flow

class MainViewModel : BaseViewModel() {

    //标题
    val titles by lazy { arrayListOf<String>("主页", "分类", "发现", "我的") }
    //未选Tab图标
    val tabNormals by lazy { listOf(R.drawable.ic_tab_main_normal,R.drawable.ic_tab_category_normal,R.drawable.ic_tab_discover_normal, R.drawable.ic_tab_me_normal) }
    //选中Tab图标
    val tabPresseds by lazy { listOf(R.drawable.ic_tab_main_pressed,R.drawable.ic_tab_category_pressed,R.drawable.ic_tab_discover_pressed,R.drawable.ic_tab_me_pressed) }
    //Fragment
    val fragments by lazy { arrayListOf(MainFragment.newInstance(), CategoryFragment.newInstance(),DiscoverFragment.newInstance(), MeFragment.newInstance()) }

    val category: MutableLiveData<List<CategoryResult>> = MutableLiveData()

    val pager by lazy {
        pager { pageNum, _ -> mDataRepository.getVideoList(pageNum, 3) }
    }

    suspend fun getMainList(): Flow<CaomeiResponse<List<MainResult>>> {
        return mDataRepository.getMainList()
    }

    /**
     * 获取分类列表
     */
    suspend fun doCategoryList() {
        launchOnIO(
            tryBlock = {
                mDataRepository.getCategoryList().run {
                    catchCode<CaomeiResponse<List<CategoryResult>>>(
                        success = { category.postValue(this.rescont) },
                        error = { error -> handlingApiExceptions(error) }
                    )
                }
            },
            catchBlock = { e -> handlingExceptions(e) }
        )
    }
}