package com.example.finance_tes2.ui.entry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finance_tes2.ui.AppViewModelProvider
import com.example.finance_tes2.ui.theme.GreenPrimary
import kotlinx.coroutines.launch

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.net.Uri
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(
    itemId: Int = 0,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToCustomerSelection: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var showIncomeTypeSheet by remember { mutableStateOf(false) }
    var showImageSourceSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    LaunchedEffect(itemId) {
        if (itemId > 0) {
            viewModel.loadItem(itemId)
        }
    }

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateUiState(viewModel.entryUiState.entryDetails.copy(imagePath = uri.toString()))
            showImageSourceSheet = false
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            viewModel.updateUiState(viewModel.entryUiState.entryDetails.copy(imagePath = tempCameraUri.toString()))
            showImageSourceSheet = false
        }
    }

    fun createImageFile(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir("Pictures")
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
    

    val currentBackStackEntry = navController.currentBackStackEntry
    val savedStateHandle = currentBackStackEntry?.savedStateHandle
    

    var selectedCustomer by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(currentBackStackEntry) {
        val result = savedStateHandle?.get<String>("selected_customer")
        if (result != null) {
            selectedCustomer = result
            viewModel.updateUiState(viewModel.entryUiState.entryDetails.copy(source = result))
            savedStateHandle.remove<String>("selected_customer")
        }
    }

    if (showIncomeTypeSheet) {
        ModalBottomSheet(
            onDismissRequest = { showIncomeTypeSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
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
                        text = "Pilih Jenis Pendapatan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(0.8f))
                    IconButton(onClick = { showIncomeTypeSheet = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))


                IncomeTypeOption(
                    title = "Pendapatan Lain",
                    description = "Uang yang masuk ke laci kasir atau rekening yang menambahkan profit atau laba bisnis.",
                    example = "Contoh:\nMenjual kemasan kardus.",
                    selected = viewModel.entryUiState.entryDetails.category == "Pendapatan Lain",
                    onSelect = {
                        viewModel.updateUiState(viewModel.entryUiState.entryDetails.copy(category = "Pendapatan Lain"))
                        showIncomeTypeSheet = false
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                androidx.compose.material3.Divider(color = Color.LightGray, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(16.dp))


                IncomeTypeOption(
                    title = "Non Pendapatan",
                    description = "Uang yang masuk ke laci kasir atau rekening yang TIDAK menambahkan profit atau laba bisnis.",
                    example = "Contoh:\nModal awal untuk kembalian ke pelanggan.",
                    selected = viewModel.entryUiState.entryDetails.category == "Non Pendapatan",
                    onSelect = {
                        viewModel.updateUiState(viewModel.entryUiState.entryDetails.copy(category = "Non Pendapatan"))
                        showIncomeTypeSheet = false
                    }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    if (showImageSourceSheet) {
        ModalBottomSheet(
            onDismissRequest = { showImageSourceSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
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
                        text = "Ambil dari",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(0.8f))
                    IconButton(
                        onClick = { showImageSourceSheet = false },
                        modifier = Modifier
                            .background(Color(0xFFEEEEEE), shape = androidx.compose.foundation.shape.CircleShape)
                            .padding(4.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                ImageSourceOption(
                    icon = Icons.Default.List,
                    label = "Galeri",
                    onClick = {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))


                ImageSourceOption(
                    icon = Icons.Default.Add,
                    label = "Kamera",
                    onClick = {
                        try {
                            val uri = createImageFile()
                            tempCameraUri = uri
                            cameraLauncher.launch(uri)
                        } catch (e: Exception) {
                            android.widget.Toast.makeText(context, "Error: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
                            Log.e("EntryScreen", "Camera Launch Error", e)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        if (itemId > 0) "Ubah Transaksi Uang Masuk" else "Buat Transaksi Uang Masuk",
                        style = MaterialTheme.typography.titleMedium
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        EntryBody(
            entryUiState = viewModel.entryUiState,
            onEntryValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            navigateToCustomerSelection = navigateToCustomerSelection,
            onIncomeTypeClick = { showIncomeTypeSheet = true },
            onUploadClick = { showImageSourceSheet = true },
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun IncomeTypeOption(
    title: String,
    description: String,
    example: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = example,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = GreenPrimary)
        )
    }
}

@Composable
fun EntryBody(
    entryUiState: EntryUiState,
    onEntryValueChange: (EntryDetails) -> Unit,
    onSaveClick: () -> Unit,
    navigateToCustomerSelection: () -> Unit,
    onIncomeTypeClick: () -> Unit,
    onUploadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val textFieldColors = androidx.compose.material3.TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        )

        // Masuk Ke
        OutlinedTextField(
            value = "Kasir Perangkat ke-49",
            onValueChange = {},
            label = { Text("Masuk Ke") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            enabled = false,
            colors = textFieldColors.copy(
                disabledIndicatorColor = Color.LightGray,
                disabledLabelColor = Color.Gray
            )
        )


        Box {
             OutlinedTextField(
                 value = entryUiState.entryDetails.source,
                 onValueChange = { },
                 label = { Text("Terima Dari") },
                 placeholder = { Text("Masukkan nama atau pilih pelanggan") },
                 trailingIcon = {
                     Icon(imageVector = Icons.Default.Person, contentDescription = "Contact")
                 },
                 modifier = Modifier.fillMaxWidth(),
                 singleLine = true,
                 readOnly = true,
                 enabled = true,
                 colors = textFieldColors
             )
             
             Box(
                 modifier = Modifier
                     .matchParentSize()
                     .clickable { navigateToCustomerSelection() }
             )
        }


        OutlinedTextField(
            value = entryUiState.entryDetails.description,
            onValueChange = { onEntryValueChange(entryUiState.entryDetails.copy(description = it)) },
            label = { Text("Keterangan") },
            placeholder = { Text("Contoh: Menjual kemasan kardus") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )


        OutlinedTextField(
            value = entryUiState.entryDetails.amount,
            onValueChange = { onEntryValueChange(entryUiState.entryDetails.copy(amount = it)) },
            label = { Text("Jumlah") },
            prefix = { Text("Rp ") },
            placeholder = { Text("0") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors
        )


        Box {
            OutlinedTextField(
                value = entryUiState.entryDetails.category,
                onValueChange = { },
                label = { Text("Jenis Pendapatan") },
                placeholder = { Text("Pilih jenis pendapatan") },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Select")
                },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = true,
                singleLine = true,
                colors = textFieldColors
            )
            
            Box(
                 modifier = Modifier
                     .matchParentSize()
                     .clickable { onIncomeTypeClick() }
             )
        }


        Text(
            text = "Upload bukti transfer / nota / kwitansi",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        if (entryUiState.entryDetails.imagePath.isNullOrEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = GreenPrimary,
                                style = Stroke(
                                    width = 2f,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                ),
                                cornerRadius = CornerRadius(16.dp.toPx())
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = onUploadClick,
                        containerColor = GreenPrimary,
                        contentColor = Color.White,
                        elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Upload")
                    }
                }
            }
            Text(
                text = "hanya menggunakan format .JPG .PNG",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {

            Row(
                 modifier = Modifier.fillMaxWidth(),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.Center
            ) {

                 Box(
                     modifier = Modifier
                         .size(150.dp, 100.dp)
                         .background(color = Color(0xFFC8E6C9), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                     contentAlignment = Alignment.Center
                 ) {
                     AsyncImage(
                         model = entryUiState.entryDetails.imagePath,
                         contentDescription = "Receipt Preview",
                         contentScale = ContentScale.Crop,
                         modifier = Modifier
                             .matchParentSize()
                             .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                     )
                 }
                 
                 Spacer(modifier = Modifier.width(24.dp))
                 
                 Column(
                     verticalArrangement = Arrangement.spacedBy(16.dp)
                 ) {

                     Icon(
                         imageVector = Icons.Default.Edit,
                         contentDescription = "Edit",
                         tint = GreenPrimary,
                         modifier = Modifier
                            .size(24.dp)
                            .clickable { onUploadClick() }
                     )
                     

                     Icon(
                         imageVector = Icons.Default.Delete,
                         contentDescription = "Delete",
                         tint = Color.Red,
                         modifier = Modifier
                            .size(24.dp)
                            .clickable { 
                                onEntryValueChange(entryUiState.entryDetails.copy(imagePath = null)) 
                            }
                     )
                 }
            }
             Text(
                text = "hanya menggunakan format .JPG .PNG",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onSaveClick,
            enabled = entryUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text(if (entryUiState.entryDetails.id > 0) "Simpan Perubahan" else "Masuk")
        }
    }
}

@Composable
fun ImageSourceOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}
