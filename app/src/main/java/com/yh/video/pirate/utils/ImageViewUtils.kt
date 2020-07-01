package com.yh.video.pirate.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.yh.video.pirate.R


/**
 * 加载图片
 */
fun ImageView.loadImage(context: Context, path: String?, placeholder: Int = R.drawable.ic_default_round_cover, useCache: Boolean = false) {
    val options = getOptions(placeholder, useCache)
    val factory =  DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    val with = DrawableTransitionOptions.with(factory)
    Glide.with(context).load(path?:placeholder).apply(options).transition(with).into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(context: Context, path: String?, placeholder: Int = R.drawable.ic_default_round_cover, useCache: Boolean = false) {
    val options = getOptions(placeholder, useCache)
    options.circleCrop()
    val factory =  DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    val with = DrawableTransitionOptions.with(factory)
    Glide.with(context).load(path?:placeholder).apply(options).transition(with).into(this)
}

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundCornerImage(context: Context, path: String?, roundingRadius: Int = 20, placeholder: Int = R.drawable.ic_default_round_cover, useCache: Boolean = false) {
    val options = getOptions(placeholder, useCache)
//    options.transform(CenterCrop())
//        .transform(RoundedCorners(roundingRadius))
    val factory =  DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    val with = DrawableTransitionOptions.with(factory)
    Glide.with(context).load(path?:placeholder).apply(options)
//        .apply(RequestOptions.centerCropTransform().transform(RoundedCorners(roundingRadius)))
        .transition(with)
        .into(this)
}

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundCornerImageSize(context: Context, path: String?, roundingRadius: Int = 20, placeholder: Int = R.drawable.ic_default_round_cover, useCache: Boolean = false, width :Int, height:Int) {
    val options = getOptions(placeholder, useCache)
    options.transform(CenterCrop(), RoundedCorners(roundingRadius))
    val factory =  DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    val with = DrawableTransitionOptions.with(factory)
    Glide.with(context).load(path?:placeholder)
        .override(width)
        .apply(options).transition(with)
        .into(this)
}



/**
 * 取消加载
 */
fun ImageView.loadClear(context: Context) {
    Glide.with(context).clear(this)
}

/**
 * 按照图片的宽高比加载
 * 使用本属性的ImageView在xml布局中的width及height属性必须为WRAP_CONTENT
 * widthProportion 为相对于屏幕宽度的比例取值范围为0.0-1.0，当widthProportion=1.0时，ImageView的宽度为屏幕宽度
 * heightProportion为相对于图片宽度的显示比例
 */
fun ImageView.loadImageByProportion(@SuppressLint("SupportAnnotationUsage") @IntRange(from = 0,  to = 1) widthProportion: Float, heightProportion: Float) {
    this.adjustViewBounds = true
    var screenWidth = 0
    val leftMargin = this.marginLeft
    val rightMargin = this.marginRight
    val wm: WindowManager = this.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    screenWidth = point.x - leftMargin - rightMargin
    var para = this.layoutParams
    para.width = (screenWidth * widthProportion).toInt()
    para.height = ViewGroup.LayoutParams.WRAP_CONTENT
    layoutParams = para
    maxWidth = (screenWidth * widthProportion).toInt()
    maxHeight = (screenWidth * widthProportion * heightProportion).toInt()
    scaleType = ImageView.ScaleType.CENTER_CROP
}


fun  ImageView.getOptions(placeholder: Int = R.drawable.ic_default_round_cover, useCache: Boolean = true): RequestOptions {

    val options = RequestOptions()
    options.placeholder(placeholder)
    options.priority(Priority.HIGH)
    if (useCache)
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
    return options
}
