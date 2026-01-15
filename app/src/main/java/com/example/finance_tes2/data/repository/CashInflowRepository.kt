package com.example.finance_tes2.data.repository

import com.example.finance_tes2.data.local.dao.CashInflowDao
import com.example.finance_tes2.data.local.entity.CashInflow
import kotlinx.coroutines.flow.Flow

class CashInflowRepository(private val cashInflowDao: CashInflowDao) {
    fun getAllCashInflows(): Flow<List<CashInflow>> = cashInflowDao.getAllCashInflows()

    suspend fun getCashInflow(id: Int): CashInflow? = cashInflowDao.getCashInflowById(id)

    suspend fun insertCashInflow(cashInflow: CashInflow) = cashInflowDao.insertCashInflow(cashInflow)

    suspend fun updateCashInflow(cashInflow: CashInflow) = cashInflowDao.updateCashInflow(cashInflow)

    suspend fun deleteCashInflow(cashInflow: CashInflow) = cashInflowDao.deleteCashInflow(cashInflow)
}
