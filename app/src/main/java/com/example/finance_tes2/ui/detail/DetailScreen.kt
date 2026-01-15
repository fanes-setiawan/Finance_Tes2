package com.example.finance_tes2.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finance_tes2.ui.AppViewModelProvider
import com.example.finance_tes2.ui.theme.DangerColor
import com.example.finance_tes2.ui.theme.GreenPrimary
import com.example.finance_tes2.utils.formatRupiah
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    itemId: Int,
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(itemId) {
        viewModel.setItemId(itemId)
    }

    val uiState by viewModel.detailUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    var showFullImage by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Detail Transaksi") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { deleteConfirmationRequired = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.cashInflow != null) {
            val item = uiState.cashInflow!!
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(text = "Tanggal Dibuat", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(
                        text = SimpleDateFormat("d MMMM yyyy - HH:mm:ss", Locale("id", "ID")).format(Date(item.date)),
                        style = MaterialTheme.typography.bodyMedium
                    )


                    Text(text = "Masuk Ke", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(text = "Kasir Perangkat ke-49", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)


                    Text(text = "Terima Dari", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(text = item.source, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)


                    Text(text = "Keterangan", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(text = if (item.description.isNotBlank()) item.description else "-", style = MaterialTheme.typography.bodyMedium)


                    Text(text = "Jumlah", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(
                         text = formatRupiah(item.amount),
                         style = MaterialTheme.typography.titleMedium,
                         fontWeight = FontWeight.Bold
                    )


                    Text(text = "Jenis Pendapatan", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(text = item.category, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)


                    Text(
                        text = "Bukti transfer / nota / kwitansi",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (!item.imagePath.isNullOrEmpty()) {
                        AsyncImage(
                            model = item.imagePath,
                            contentDescription = "Bukti Transfer",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                                .clickable { showFullImage = true },
                            contentScale = ContentScale.Fit
                        )
                    } else {

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Outlined.Info, contentDescription = null, tint = Color.LightGray)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))


                Column(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = { navigateToEdit(item.id) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Ubah transaksi" , color = Color.White)
                    }

                    TextButton(
                        onClick = { deleteConfirmationRequired = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Hapus", color = DangerColor)
                    }
                }
            }
        }

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteItem()
                        navigateBack()
                    }
                },
                onDeleteCancel = { deleteConfirmationRequired = false }
            )
        }

        if (showFullImage && uiState.cashInflow?.imagePath != null) {
            FullScreenImageDialog(
                imagePath = uiState.cashInflow!!.imagePath!!,
                onDismiss = { showFullImage = false }
            )
        }
    }
}

@Composable
fun FullScreenImageDialog(
    imagePath: String,
    onDismiss: () -> Unit
) {
    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            
            AsyncImage(
                model = imagePath,
                contentDescription = "Full Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )


            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(Color(0x80000000), shape = androidx.compose.foundation.shape.CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = { Text("Hapus Transaksi") },
        text = { Text("Apakah Anda yakin ingin menghapus transaksi ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        },
        confirmButton = {
            Button(onClick = onDeleteConfirm, colors = ButtonDefaults.buttonColors(containerColor = DangerColor)) {
                Text("Hapus")
            }
        }
    )
}
