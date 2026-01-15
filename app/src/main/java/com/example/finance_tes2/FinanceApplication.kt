package com.example.finance_tes2

import android.app.Application
import com.example.finance_tes2.data.AppContainer
import com.example.finance_tes2.data.AppDataContainer

class FinanceApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
