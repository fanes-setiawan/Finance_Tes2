package com.example.finance_tes2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cash_inflow")
data class CashInflow(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val source: String, // e.g. "Kasir Perangkat ke-49"
    val description: String,
    val date: Long, // Epoch millis
    val category: String, // e.g. "Pendapatan Lain", "Non Pendapatan"
    val imagePath: String? = null
)
