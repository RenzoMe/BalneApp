package com.example.balneapp.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.balneapp.BalneAppApplication

@Composable
inline fun <reified VM : ViewModel> balneAppViewModel(key: String? = null): VM {
    val application = LocalContext.current.applicationContext as BalneAppApplication
    return viewModel(key = key, factory = application.viewModelFactory)
}
