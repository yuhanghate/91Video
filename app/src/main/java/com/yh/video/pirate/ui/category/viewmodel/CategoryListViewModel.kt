package com.yh.video.pirate.ui.category.viewmodel

import androidx.paging.PagingData
import com.yh.video.pirate.base.BaseViewModel
import com.yh.video.pirate.repository.network.result.CategoryList
import com.yh.video.pirate.ui.category.adapter.CategoryListAdapter
import com.yh.video.pirate.ui.category.fragment.CategoryListFragment
import com.yh.video.pirate.utils.pager
import kotlinx.coroutines.flow.Flow

class CategoryListViewModel : BaseViewModel() {

    val adapter by lazy { CategoryListAdapter() }

    private val mFragmens:ArrayList<CategoryListFragment> = arrayListOf()

    val mTitles = listOf<String>("  最新  ","  热门  ","  最多喜欢  ")

    fun getFragments(id:Int): List<CategoryListFragment> {
        if(mFragmens.isNotEmpty()) return mFragmens
        mFragmens.add(CategoryListFragment.newInstance(id,CategoryListFragment.TYPE_NEW))
        mFragmens.add(CategoryListFragment.newInstance(id,CategoryListFragment.TYPE_HOT))
        mFragmens.add(CategoryListFragment.newInstance(id,CategoryListFragment.TYPE_LIKE))
        return mFragmens
    }

    /**
     * 分类列表
     */
    suspend fun getCategoryList(id: Int, type: String): Flow<PagingData<CategoryList>> {
        return pager(
            callback = { pageNum: Int, _: Int ->
                return@pager mDataRepository.getCategoryList(
                    id = id,
                    type = type,
                    pageNum = pageNum
                )
            }
        )
    }
}