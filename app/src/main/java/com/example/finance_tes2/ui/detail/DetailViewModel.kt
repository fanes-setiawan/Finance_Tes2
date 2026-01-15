package com.example.finance_tes2.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finance_tes2.data.local.entity.CashInflow
import com.example.finance_tes2.data.repository.CashInflowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetailUiState(
    val cashInflow: CashInflow? = null
)

class DetailViewModel(
    private val repository: CashInflowRepository
) : ViewModel() {

    private val _itemId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val detailUiState: StateFlow<DetailUiState> = _itemId
        .filterNotNull()
        .flatMapLatest { id ->
            repository.getAllCashInflows()
                .map { list -> list.find { it.id == id } }
                .map { DetailUiState(it) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState()
        )

    fun setItemId(itemId: Int) {
        _itemId.value = itemId
    }

    suspend fun deleteItem() {
        detailUiState.value.cashInflow?.let {
            repository.deleteCashInflow(it)
        }
    }
}
