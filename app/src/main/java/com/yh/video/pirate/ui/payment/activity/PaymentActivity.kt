package com.yh.video.pirate.ui.payment.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.View
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode.*
import com.gyf.immersionbar.ImmersionBar
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityPaymentBinding
import com.yh.video.pirate.ui.payment.viewmodel.PaymentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

/**
 * 充值
 */
class PaymentActivity : BaseActivity<ActivityPaymentBinding, PaymentViewModel>(),
    PurchasesUpdatedListener {

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

    private lateinit var billingClient: BillingClient


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

    override fun initData() {
        super.initData()
        initGooglePay()
    }

    /**
     * 连接谷歌支付
     */
    private fun initGooglePay() {
        billingClient = BillingClient.newBuilder(this).setListener(this).build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  OK) {
                    // TODO 支付完成
                    billingResult.responseCode
                }else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                    // TODO 用户取消了支付

                }else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                    // TODO 商品已经购买过（重复购买了此商品，如果需要支持重复购买，需要将商品购买成功后消费掉）
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
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

    /**
     * 查询商品列表
     */
    suspend fun querySkuDetails():SkuDetailsResult {
        val skuList = ArrayList<String>()
        skuList.add("premium_upgrade")
        skuList.add("gas")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        val skuDetailsResult = withContext(Dispatchers.IO) {
            billingClient.querySkuDetails(params.build())
        }
        return skuDetailsResult
        // Process the result.
    }

    fun setGoodsPay() {
//        val skuList = ArrayList<String>()
//        skuList.add("premium_upgrade")
//        skuList.add("gas")
//        val params = SkuDetailsParams.newBuilder()
//        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
//        val flowParams = BillingFlowParams.newBuilder()
//            .setSkuDetails(params.build())
//            .build()
//        val responseCode = billingClient.launchBillingFlow(this, flowParams)
    }

    override fun onPurchasesUpdated(responseCode: BillingResult?, purchases: MutableList<Purchase>?) {
        if (responseCode?.responseCode == OK && purchases != null) {
            // TODO 支付完成
        } else if (responseCode?.responseCode == USER_CANCELED) {
            // TODO 用户取消了支付
        } else if (responseCode?.responseCode == ITEM_ALREADY_OWNED) {
            // TODO 商品已经购买过（重复购买了此商品，如果需要支持重复购买，需要将商品购买成功后消费掉）
        } else {
            // Handle any other error codes.
        }
    }
}