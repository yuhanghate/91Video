package com.yh.video.pirate.ui.main.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityMainBinding
import com.yh.video.pirate.databinding.LayoutMainTabBinding
import com.yh.video.pirate.ui.main.viewmodel.MainViewModel
import com.yh.video.pirate.utils.bindViewPager
import com.yh.video.pirate.utils.evaluate
import com.yh.video.pirate.utils.getColorCompat
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener

/**
 * 主页
 */
open class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onStatusColor(): Int {
        return R.color.md_grey_900
    }

    override fun onNavigationBarColor(): Int {
        return R.color.md_grey_900
    }

    override fun initView() {
        super.initView()
        initMagicIndicator1()
        initViewPager()
    }


    private fun initViewPager() {
        mBinding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mViewModel.fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return mViewModel.fragments[position]
            }
        }
    }

    private fun initMagicIndicator1() {
        mBinding.magicIndicator.setBackgroundResource(R.color.md_grey_900)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mViewModel.titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val commonPagerTitleView = CommonPagerTitleView(context)

                // load custom layout
                val inflate = LayoutMainTabBinding.inflate(layoutInflater, null, false)
                val titleImg = inflate.titleIv
                val titleText = inflate.titleTv
                titleImg.setImageResource(mViewModel.tabNormals[index])
                titleText.text = mViewModel.titles[index]
                commonPagerTitleView.setContentView(inflate.root)
                commonPagerTitleView.onPagerTitleChangeListener = object :
                    OnPagerTitleChangeListener {
                    override fun onSelected(index: Int, totalCount: Int) {
                        titleText.setTextColor(getColorCompat(R.color.md_yellow_600))
                        titleImg.setImageResource(mViewModel.tabPresseds[index])
                    }

                    override fun onDeselected(index: Int, totalCount: Int) {
                        titleText.setTextColor(getColorCompat(R.color.md_grey_300))
                        titleImg.setImageResource(mViewModel.tabNormals[index])
                    }

                    override fun onLeave(
                        index: Int,
                        totalCount: Int,
                        leavePercent: Float,
                        leftToRight: Boolean
                    ) {
                        val scaleImage = 1.0f + (0.8f - 1.0f) * leavePercent
                        val scaleText = 1.15f + (1f - 1.15f) * leavePercent
                        titleImg.scaleX = scaleImage
                        titleImg.scaleY = scaleImage
                        titleText.scaleX = scaleText
                        titleText.scaleY = scaleText
                        // 1. 颜色变换
                        val finalColor: Int = evaluate(
                            leavePercent,
                            getColorCompat(R.color.md_yellow_600),
                            getColorCompat(R.color.md_grey_300)

                        )
                        titleText.setTextColor(finalColor)
                        titleImg.drawable.setTint(finalColor)

                    }

                    override fun onEnter(
                        index: Int,
                        totalCount: Int,
                        enterPercent: Float,
                        leftToRight: Boolean
                    ) {
                        val scaleImage = 0.8f + (1.0f - 0.8f) * enterPercent
                        val scaleText = 1f + (1.15f - 1f) * enterPercent
                        titleImg.scaleX = scaleImage
                        titleImg.scaleY = scaleImage
                        titleText.scaleX = scaleText
                        titleText.scaleY = scaleText
                        val finalColor: Int = evaluate(
                            enterPercent,
                            getColorCompat(R.color.md_grey_300),
                            getColorCompat(R.color.md_yellow_600)
                        )
                        titleText.setTextColor(finalColor)
                        titleImg.drawable.setTint(finalColor)
                    }
                }
                commonPagerTitleView.setOnClickListener { mBinding.viewPager.currentItem = index }
                return commonPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }
        mBinding.magicIndicator.navigator = commonNavigator
        mBinding.magicIndicator.bindViewPager(mBinding.viewPager)
    }

    override fun onBackPressedSupport() {

    }

}