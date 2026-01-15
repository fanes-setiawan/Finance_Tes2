package com.example.finance_tes2.utils

import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(amount: Double): String {
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(amount)
}

fun formatRupiahNoSymbol(amount: Double): String {
     val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getNumberInstance(localeID)
    return numberFormat.format(amount)
}
