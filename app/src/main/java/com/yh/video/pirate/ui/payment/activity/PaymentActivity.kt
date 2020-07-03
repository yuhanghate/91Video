package com.yh.video.pirate.ui.payment.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityPaymentBinding
import com.yh.video.pirate.ui.payment.viewmodel.PaymentViewModel
import java.math.BigDecimal

/**
 * 充值
 */
class PaymentActivity : BaseActivity<ActivityPaymentBinding, PaymentViewModel>() {

    private val views by lazy {
        listOf(
            mBinding.btnProduct48,
            mBinding.btnProduct98,
            mBinding.btnProduct198,
            mBinding.btnProduct298
        )
    }

    private val amounts by lazy {
        listOf(mBinding.amount48Tv, mBinding.amount98Tv, mBinding.amount198Tv, mBinding.amount298Tv)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PaymentActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.activity_payment
    }

    override fun initStatusTool() {
        ImmersionBar.with(this)
            .statusBarView(mBinding.statusBar)
            .navigationBarColor(R.color.md_black_1000) //导航栏颜色，不写默认黑色
            .init()
    }

    override fun initView() {
        super.initView()
        amounts.forEach { it.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG }
    }

    override fun onClick() {
        super.onClick()
        mBinding.btnBack.setOnClickListener { onBackPressedSupport() }
        views.forEach { it.setOnClickListener {view -> setSelectProduct(view) } }
    }

    /**
     * 选择金额
     */
    private fun setSelectProduct(view: View) {
        val decimalFormat = BigDecimal(0.00);//构造方法的字符格式这里如果小数不足2位,会以0补足.
        views.forEach {
            if (it == view) {
                it.setBackgroundResource(R.drawable.bg_payment_selected)
                mViewModel.currentAmount = it.tag.toString().toInt()
                mBinding.btnPayment.text = "支付 ¥${mViewModel.currentAmount}.00"
            } else {
                it.setBackgroundResource(R.drawable.bg_payment_normal)
            }
        }
    }
}