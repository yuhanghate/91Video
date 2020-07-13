package com.yh.video.pirate.payment

import com.android.billingclient.api.BillingClient

class PaymentManager {

//    private var billingClient: BillingClient = BillingClient.newBuilder(context).setListener { responseCode: Int, purchases: MutableList<Purchase>? ->
//        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
//            // TODO 支付完成
//        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
//            // Handle an error caused by a user cancelling the purchase flow.
//            // TODO 用户取消了支付
//        } else if (responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
//            // Handle an error caused by a user cancelling the purchase flow.
//            // TODO 商品已经购买过（重复购买了此商品，如果需要支持重复购买，需要将商品购买成功后消费掉）
//        } else {
//            // Handle any other error codes.
//        }
//    }.build()
//
//    lateinit private var billingClient: BillingClient
//    billingClient = BillingClient.newBuilder(context).setListener(this).build()
//    billingClient.startConnection(object : BillingClientStateListener {
//        override fun onBillingSetupFinished(billingResult: BillingResult) {
//            if (billingResult.responseCode ==  BillingResponseCode.OK) {
//                // The BillingClient is ready. You can query purchases here.
//            }
//        }
//        override fun onBillingServiceDisconnected() {
//            // Try to restart the connection on the next request to
//            // Google Play by calling the startConnection() method.
//        }
//    })
}