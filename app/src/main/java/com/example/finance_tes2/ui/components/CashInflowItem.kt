package com.example.finance_tes2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finance_tes2.data.local.entity.CashInflow
import com.example.finance_tes2.ui.theme.GreenPrimary
import com.example.finance_tes2.utils.formatRupiah
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CashInflowItem(
    item: CashInflow,
    onItemClick: (CashInflow) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            val context = androidx.compose.ui.platform.LocalContext.current
            val imageLoader = androidx.compose.runtime.remember {
                coil.ImageLoader.Builder(context)
                    .components {
                        add(coil.decode.SvgDecoder.Factory())
                    }
                    .build()
            }

            coil.compose.AsyncImage(
                model = "file:///android_asset/finance_icon.svg",
                imageLoader = imageLoader,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF5F5F5), shape = androidx.compose.foundation.shape.CircleShape)
                    .padding(8.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(item.date)),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )

                        Text(
                            text = item.source,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Kasir perangkat ke-49",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }


                    Text(
                        text = formatRupiah(item.amount),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                

                if (item.description.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color(0xFFEEEEEE),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        coil.compose.AsyncImage(
                            model = "file:///android_asset/list_icon.svg",
                            imageLoader = imageLoader,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
            

        }
    }
}
