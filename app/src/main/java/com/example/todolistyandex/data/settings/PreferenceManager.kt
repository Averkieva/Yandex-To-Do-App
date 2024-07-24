package com.example.todolistyandex.data.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesManager(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _themePreferenceFlow = MutableStateFlow(themePreference)

    var themePreference: ThemePreference
        get() {
            val themeValue = preferences.getString(KEY_THEME, ThemePreference.SYSTEM.name)
            return ThemePreference.valueOf(themeValue ?: ThemePreference.SYSTEM.name)
        }
        set(value) {
            preferences.edit {
                putString(KEY_THEME, value.name)
            }
            _themePreferenceFlow.value = value
        }

    val themePreferenceFlow: Flow<ThemePreference> = _themePreferenceFlow.asStateFlow()

    companion object {
        private const val PREFS_NAME = "todo_preferences"
        private const val KEY_THEME = "theme_preference"
    }
}

enum class ThemePreference {
    LIGHT, DARK, SYSTEM
}
