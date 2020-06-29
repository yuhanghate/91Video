package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class RecommendedLikeResult(
    var coverpath: String?,
    var id: Int?,
    var introduction: String?,
    var labls: List<Labl>?,
    var pageviews: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<RecommendedLikeResult>() {
            override fun areItemsTheSame(
                oldItem: RecommendedLikeResult,
                newItem: RecommendedLikeResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecommendedLikeResult,
                newItem: RecommendedLikeResult
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

data class Labl(
    var id: Int?,
    var name: String?
)