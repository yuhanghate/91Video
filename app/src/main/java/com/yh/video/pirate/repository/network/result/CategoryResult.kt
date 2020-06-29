package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class CategoryResult(
    val icopath: String,
    val id: Int,
    val name: String,
    val order: Int
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<CategoryResult>() {
            override fun areItemsTheSame(
                oldItem: CategoryResult,
                newItem: CategoryResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryResult,
                newItem: CategoryResult
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}