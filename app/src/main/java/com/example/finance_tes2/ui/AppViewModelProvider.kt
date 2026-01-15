package com.example.finance_tes2.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finance_tes2.FinanceApplication
import com.example.finance_tes2.ui.detail.DetailViewModel
import com.example.finance_tes2.ui.entry.EntryViewModel
import com.example.finance_tes2.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(financeApplication().container.cashInflowRepository)
        }
        initializer {
            EntryViewModel(financeApplication().container.cashInflowRepository)
        }
        initializer {
            DetailViewModel(financeApplication().container.cashInflowRepository)
        }
        // Add other ViewModels here
    }
}

fun CreationExtras.financeApplication(): FinanceApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FinanceApplication)
