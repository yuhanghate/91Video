package com.yh.video.pirate.ui.main.fragment

import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentCategoryBinding
import com.yh.video.pirate.ui.main.viewmodel.CategoryViewModel

class CategoryFragment:BaseFragment<FragmentCategoryBinding, CategoryViewModel>() {

    companion object{
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }
    override fun onLayoutId(): Int {
        return R.layout.fragment_category
    }
}