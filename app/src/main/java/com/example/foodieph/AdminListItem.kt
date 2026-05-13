package com.example.foodieph

data class AdminListItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val detail: String,
    val extra: String,
    val status: String
)

data class AdminUserItem(
    val uid: String,
    val name: String,
    val phone: String
)

data class AdminOrderSummary(
    val orderId: String,
    val itemCount: Int,
    val totalAmount: Int,
    val userId: String,
    val placedAtMs: Long = 0L
)
