package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class Category(
    val icopath: String,
    val id: Int,
    val name: String,
    val order: Int
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}