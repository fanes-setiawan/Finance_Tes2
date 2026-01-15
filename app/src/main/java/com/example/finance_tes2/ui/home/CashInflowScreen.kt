package com.example.finance_tes2.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.TextButton
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finance_tes2.R
import com.example.finance_tes2.ui.AppViewModelProvider
import com.example.finance_tes2.ui.components.CashInflowItem
import com.example.finance_tes2.ui.components.EmptyStateView
import com.example.finance_tes2.ui.theme.BackgroundColor
import com.example.finance_tes2.ui.theme.GreenPrimary
import com.example.finance_tes2.utils.formatRupiah
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashInflowScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToEntry: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    var showRadioButtonSheet by remember { mutableStateOf(false) }
    var showDateRangePicker by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var startWithInputMode by remember { mutableStateOf(false) }


    if (showRadioButtonSheet) {
        ModalBottomSheet(
            onDismissRequest = { showRadioButtonSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
             var selectedOption by remember { mutableStateOf("Hari ini") }
             var customDateRange by remember { mutableStateOf("13 Apr 2024 - 23 Apr 2024") }

             Column(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(16.dp)
             ) {

                 Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     Spacer(modifier = Modifier.weight(1f))
                     Text(
                         text = "Pilih Periode",
                         style = MaterialTheme.typography.titleLarge,
                         modifier = Modifier.weight(2f),
                         textAlign = TextAlign.Center,
                     )
                     IconButton(onClick = { showRadioButtonSheet = false }) {
                         Icon(imageVector = Icons.Outlined.Close, contentDescription = "Close")
                     }
                 }
                 
                 Spacer(modifier = Modifier.height(16.dp))

                 val options = listOf("Hari ini", "Kemarin", "7 hari terakhir", "Pilih tanggal sendiri")

                 options.forEach { option ->
                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .clickable { selectedOption = option }
                             .padding(vertical = 8.dp),
                         verticalAlignment = Alignment.CenterVertically
                     ) {
                         RadioButton(
                             selected = (selectedOption == option),
                             onClick = { selectedOption = option },
                             colors = RadioButtonDefaults.colors(selectedColor = GreenPrimary)
                         )
                         Text(
                             text = option,
                             style = MaterialTheme.typography.bodyLarge,
                             modifier = Modifier.padding(start = 8.dp)
                         )
                     }
                     

                     if (option == "Pilih tanggal sendiri" && selectedOption == option) {
                         Column(modifier = Modifier.padding(start = 48.dp, bottom = 8.dp)) {
                             Text(
                                 text = "Periode",
                                 style = MaterialTheme.typography.bodySmall,
                                 color = Color.Gray
                             )
                              Box {
                                  OutlinedTextField(
                                      value = customDateRange,
                                      onValueChange = { customDateRange = it },
                                      modifier = Modifier.fillMaxWidth(),
                                      readOnly = true, 
                                      enabled = true,
                                      colors = androidx.compose.material3.TextFieldDefaults.colors(
                                          disabledContainerColor = Color.Transparent,
                                          errorContainerColor = Color.Transparent,
                                          focusedContainerColor = Color.Transparent,
                                          unfocusedContainerColor = Color.Transparent
                                      )
                                  )
                                  Box(
                                      modifier = Modifier
                                          .matchParentSize()
                                          .clickable { 
                                              startWithInputMode = true
                                              showDateRangePicker = true 
                                          }
                                  )
                              }
                         }
                     }
                 }

                 Spacer(modifier = Modifier.height(24.dp))

                 Button(
                     onClick = { 
                         showRadioButtonSheet = false 
                         navigateToEntry()
                     },
                     modifier = Modifier.fillMaxWidth(),
                     colors = ButtonDefaults.buttonColors(
                         containerColor = GreenPrimary
                     )
                 ) {
                     Text("Terapkan")
                 }
                 
                 Spacer(modifier = Modifier.height(32.dp))
             }
        }
    }


    if (showDateRangePicker) {
        val datePickerState = rememberDateRangePickerState(
            initialDisplayMode = if (startWithInputMode) DisplayMode.Input else DisplayMode.Picker
        )

        DatePickerDialog(
            onDismissRequest = { showDateRangePicker = false },
            confirmButton = {
                TextButton(onClick = { showDateRangePicker = false }) {
                    Text("Terapkan", color = GreenPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDateRangePicker = false }) {
                    Text("Batal", color = GreenPrimary)
                }
            }
        ) {
            DateRangePicker(
                state = datePickerState,
                showModeToggle = true,
                title = {
                    val titleText = if (datePickerState.displayMode == DisplayMode.Input) {
                        "Pilih tanggal"
                    } else {
                        "Tanggal awal - Tanggal Akhir"
                    }
                    Text(
                        text = titleText,
                        modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                headline = {
                    if (datePickerState.displayMode == DisplayMode.Input) {
                         Text(
                             text = "Masukkan tgl",
                             modifier = Modifier.padding(start = 24.dp, end = 12.dp, bottom = 12.dp),
                             style = MaterialTheme.typography.headlineMedium
                         )
                    } else {
                        DateRangePickerDefaults.DateRangePickerHeadline(
                            selectedStartDateMillis = datePickerState.selectedStartDateMillis,
                            selectedEndDateMillis = datePickerState.selectedEndDateMillis,
                            displayMode = datePickerState.displayMode,
                            dateFormatter = DatePickerDefaults.dateFormatter(),
                            modifier = Modifier.padding(start = 24.dp, end = 12.dp, bottom = 12.dp)
                        )
                    }
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Uang Masuk") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                     androidx.compose.material3.IconButton(onClick = navigateBack) {
                         Icon(
                             imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                             contentDescription = "Back",
                             tint = Color.Black
                         )
                     }
                }
            )
        },
        floatingActionButton = {

        },

        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = navigateToEntry,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
                ) {
                    Text("Buat transaksi uang masuk", color = Color.White)
                }
            }
        },
        containerColor = BackgroundColor
    ) { innerPadding ->
        if (homeUiState.itemList.isEmpty()) {
            EmptyStateView(
                onSetPeriodClick = { 
                    startWithInputMode = false
                    showDateRangePicker = true 
                },
                onCreateTransactionClick = { showRadioButtonSheet = true },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            Column(
                 modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(BackgroundColor)
            ) {

                OutlinedButton(
                    onClick = { showRadioButtonSheet = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenPrimary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GreenPrimary),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(text = "Periode", style = MaterialTheme.typography.bodyMedium)
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }
                }


                val groupedItems = homeUiState.itemList.groupBy { 
                    SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID")).format(Date(it.date))
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    groupedItems.forEach { (date, items) ->
                        item {
                            val totalAmount = items.sumOf { it.amount }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = date,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                                Text(
                                    text = formatRupiah(totalAmount),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = GreenPrimary,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            }
                        }
                        
                        items(items) { item ->
                            CashInflowItem(
                                item = item,
                                onItemClick = { navigateToDetail(it.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
