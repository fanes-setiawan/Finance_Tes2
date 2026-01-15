package com.example.finance_tes2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finance_tes2.data.local.entity.CashInflow
import kotlinx.coroutines.flow.Flow

@Dao
interface CashInflowDao {
    @Query("SELECT * FROM cash_inflow ORDER BY date DESC")
    fun getAllCashInflows(): Flow<List<CashInflow>>

    @Query("SELECT * FROM cash_inflow WHERE id = :id")
    suspend fun getCashInflowById(id: Int): CashInflow?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashInflow(cashInflow: CashInflow)

    @Update
    suspend fun updateCashInflow(cashInflow: CashInflow)

    @Delete
    suspend fun deleteCashInflow(cashInflow: CashInflow)
}
