package com.example.finance_tes2.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finance_tes2.ui.theme.GreenPrimary


data class Customer(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String? = null,
    val isMember: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerSelectionScreen(
    navigateBack: () -> Unit,
    onCustomerSelected: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCustomerId by remember { mutableStateOf<Int?>(null) }


    val customers = listOf(
        Customer(1, "Tatiana Rosser", "08123456789", "tatianarosser@mail.com", true),
        Customer(2, "Erin Dorwart", "", null, false),
        Customer(3, "Corey Rosser", "", null, false),
        Customer(4, "Mira Workman", "", null, true),
        Customer(5, "Emery Kenter", "", null, false),
        Customer(6, "Alena Geidt", "", null, false),
        Customer(7, "Jocelyn Gouse", "08123456789", null, true),
        Customer(8, "Maren Culhane", "", null, false),
        Customer(9, "Marley Stanton", "08123456789", null, true),
        Customer(10, "Cooper Dias", "", "johndoe@mail.com", true),
    )

    val filteredCustomers = if (searchQuery.isBlank()) {
        customers
    } else {
        customers.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pelanggan", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White,
        bottomBar = {
             Column(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(16.dp)
                     .background(Color.White)
             ) {
                 Button(
                     onClick = { },
                     modifier = Modifier.fillMaxWidth(),
                     colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                     shape = RoundedCornerShape(50)
                 ) {
                     Text("Tambah pelanggan baru" , color = Color.White)
                 }
             }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari pelanggan di sini") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    disabledContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredCustomers) { customer ->
                    CustomerItem(
                        customer = customer,
                        isSelected = customer.id == selectedCustomerId,
                        onClick = {
                            selectedCustomerId = customer.id
                            onCustomerSelected(customer.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerItem(
    customer: Customer,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F5E9)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = customer.name.first().toString(),
                color = GreenPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = customer.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (customer.isMember) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = Color(0xFFE1F5FE),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Member",
                            color = Color(0xFF039BE5),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            
            if (customer.phone.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = customer.phone, color = Color.Gray, fontSize = 14.sp)
                }
            }
            
             if (!customer.email.isNullOrEmpty()) {
                Row(
                    modifier = Modifier.padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = customer.email, color = Color.Gray, fontSize = 14.sp)
                }
            }
        }

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = GreenPrimary
            )
        }
    }
}
