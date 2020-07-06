package com.yh.video.pirate.ui.category.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityCategoryListBinding
import com.yh.video.pirate.ui.category.viewmodel.CategoryListViewModel
import com.yh.video.pirate.ui.main.viewmodel.CategoryViewModel
import com.yh.video.pirate.utils.bindViewPager
import com.yh.video.pirate.utils.dp
import com.yh.video.pirate.utils.sp
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * 分类列表: 国产自拍/高清無碼
 */
class CategoryListActivity:BaseActivity<ActivityCategoryListBinding, CategoryListViewModel>() {

    companion object{
        const val ID = "ID"
        const val NAME = "NAME"
        fun start(context: Context, type:Int, name:String) {
            val intent = Intent(context, CategoryListActivity::class.java)
            intent.putExtra(ID, type)
            intent.putExtra(NAME, name)
            context.startActivity(intent)
        }
    }

    private fun getId() = intent.getIntExtra(ID, 0)
    private fun getName() = intent.getStringExtra(NAME)

    override fun onLayoutId(): Int {
        return R.layout.activity_category_list
    }

    override fun onStatusColor(): Int {
        return R.color.md_grey_900
    }

    override fun initView() {
        super.initView()
        initMagicIndicator()
        initViewPager()
    }

    override fun onClick() {
        super.onClick()
        mBinding.btnBack.setOnClickListener { onBackPressedSupport() }
        mBinding.titleTv.text = getName()
    }

    /**
     * 初始化ViewPager
     */
    private fun initViewPager() {
        mBinding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mViewModel.getFragments(getId()).size
            }

            override fun createFragment(position: Int): Fragment {
                return mViewModel.getFragments(getId())[position]
            }
        }
    }

    /**
     * 头部Tab
     */
    private fun initMagicIndicator() {
        mBinding.magicIndicator.setBackgroundResource(R.color.md_grey_900)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.35f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mViewModel.mTitles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mViewModel.mTitles[index]
                simplePagerTitleView.normalColor = Color.parseColor("#EEEEEE")
                simplePagerTitleView.selectedColor = Color.parseColor("#2196F3")
                simplePagerTitleView.textSize = 15f
                simplePagerTitleView.setOnClickListener { mBinding.viewPager.setCurrentItem(index, false) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = WrapPagerIndicator(context)
                indicator.horizontalPadding = 15.dp
                indicator.fillColor = Color.parseColor("#F5F5F5")
                return indicator
            }
        }
        mBinding.magicIndicator.navigator = commonNavigator
        mBinding.magicIndicator.bindViewPager(mBinding.viewPager)
    }
}