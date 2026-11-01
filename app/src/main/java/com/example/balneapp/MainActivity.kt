package com.example.balneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.balneapp.navigation.NavGraph
import com.example.balneapp.ui.theme.BalneAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BalneAppTheme {
                NavGraph()
            }
        }
    }
}