package com.example.finance_tes2.ui.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.finance_tes2.data.local.entity.CashInflow
import com.example.finance_tes2.data.repository.CashInflowRepository
import java.text.SimpleDateFormat
import java.util.Locale

class EntryViewModel(private val repository: CashInflowRepository) : ViewModel() {
    var entryUiState by mutableStateOf(EntryUiState())
        private set

    fun updateUiState(entryDetails: EntryDetails) {
        entryUiState = EntryUiState(entryDetails = entryDetails, isEntryValid = validateInput(entryDetails))
    }

    suspend fun saveItem() {
        if (validateInput(entryUiState.entryDetails)) {
            repository.insertCashInflow(entryUiState.entryDetails.toCashInflow())
        }
    }

    suspend fun loadItem(itemId: Int) {
        val item = repository.getCashInflow(itemId)
        item?.let {
            updateUiState(it.toEntryDetails())
        }
    }

    private fun validateInput(uiState: EntryDetails = entryUiState.entryDetails): Boolean {
        return with(uiState) {
            amount.isNotBlank() && source.isNotBlank() && category.isNotBlank()
        }
    }
}

data class EntryUiState(
    val entryDetails: EntryDetails = EntryDetails(),
    val isEntryValid: Boolean = false
)

data class EntryDetails(
    val id: Int = 0,
    val amount: String = "",
    val source: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val category: String = "",
    val imagePath: String? = null
)

fun EntryDetails.toCashInflow(): CashInflow = CashInflow(
    id = id,
    amount = amount.toDoubleOrNull() ?: 0.0,
    source = source,
    description = description,
    date = date,
    category = category,
    imagePath = imagePath
)

fun CashInflow.toEntryDetails(): EntryDetails = EntryDetails(
    id = id,
    amount = if (amount % 1.0 == 0.0) amount.toLong().toString() else amount.toString(),
    source = source,
    description = description,
    date = date,
    category = category,
    imagePath = imagePath
)
