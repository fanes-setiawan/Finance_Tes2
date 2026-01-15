package com.example.finance_tes2.data

import android.content.Context
import com.example.finance_tes2.data.local.AppDatabase
import com.example.finance_tes2.data.repository.CashInflowRepository

interface AppContainer {
    val cashInflowRepository: CashInflowRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val cashInflowRepository: CashInflowRepository by lazy {
        CashInflowRepository(AppDatabase.getDatabase(context).cashInflowDao())
    }
}
