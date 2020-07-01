package com.yh.video.pirate.ui.main.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemDiscoverBinding
import com.yh.video.pirate.repository.network.result.DiscoverResult
import com.yh.video.pirate.repository.network.result.VideoResult
import com.yh.video.pirate.utils.loadImage
import com.yuhang.novel.pirate.base.BaseViewHolder
import java.math.BigDecimal

class ItemDiscoverVH(parent:ViewGroup):BaseViewHolder<VideoResult,ItemDiscoverBinding>(parent, R.layout.item_discover) {
    override fun bindData(data: VideoResult, position: Int) {
        val amount = data.pageviews?:12000
        mBinding.coverIv.loadImage(mContext,data.coverpath)
        mBinding.introTv.text = data.title
        mBinding.amountTv.text = intChange2Str(8089+amount)
    }

    /**
     * 播放量转成次数
     */
    private fun intChange2Str(number: Int): String? {
        val str: String
        str = when {
            number <= 0 -> {
                ""
            }
            number < 10000 -> {
                number.toString()
            }
            else -> {
                val d = number.toDouble()
                val num = d / 10000 //1.将数字转换成以万为单位的数字
                val b = BigDecimal(num)
                val f1: Double =
                    b.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble() //2.转换后的数字四舍五入保留小数点后一位;
                f1.toString() + "万次观看"
            }
        }
        return str
    }
}