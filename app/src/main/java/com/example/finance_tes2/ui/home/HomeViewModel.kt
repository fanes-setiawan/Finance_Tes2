package com.example.finance_tes2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finance_tes2.data.local.entity.CashInflow
import com.example.finance_tes2.data.repository.CashInflowRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val itemList: List<CashInflow> = listOf(),
    val totalAmount: Double = 0.0
)

class HomeViewModel(private val repository: CashInflowRepository) : ViewModel() {
    
    val homeUiState: StateFlow<HomeUiState> = repository.getAllCashInflows()
        .map { items -> 
            HomeUiState(
                itemList = items,
                totalAmount = items.sumOf { it.amount }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )
}
