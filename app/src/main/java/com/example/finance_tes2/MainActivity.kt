package com.example.finance_tes2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.finance_tes2.ui.navigation.FinanceApp
import com.example.finance_tes2.ui.theme.Finance_Tes2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Finance_Tes2Theme {
                FinanceApp()
            }
        }
    }
}
