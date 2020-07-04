package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class RecommendedLike(
    var coverpath: String?,
    var id: Int?,
    var introduction: String?,
    var labls: List<Labl>?,
    var pageviews: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<RecommendedLike>() {
            override fun areItemsTheSame(
                oldItem: RecommendedLike,
                newItem: RecommendedLike
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecommendedLike,
                newItem: RecommendedLike
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