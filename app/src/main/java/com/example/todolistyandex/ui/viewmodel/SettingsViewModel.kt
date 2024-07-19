package com.example.todolistyandex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistyandex.data.settings.PreferencesManager
import com.example.todolistyandex.data.settings.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _themePreference = MutableStateFlow(preferencesManager.themePreference)
    val themePreference: StateFlow<ThemePreference> = _themePreference

    fun updateThemePreference(themePreference: ThemePreference) {
        viewModelScope.launch {
            preferencesManager.themePreference = themePreference
            _themePreference.value = themePreference
        }
    }
}