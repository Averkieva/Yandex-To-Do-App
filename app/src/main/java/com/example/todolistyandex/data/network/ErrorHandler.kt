package com.example.todolistyandex.data.network

/**
 * Object for handling error messages based on HTTP response codes, providing
 * user-friendly messages for different error scenarios.
 */

object ErrorHandler {
    fun getErrorMessage(code: Int): String {
        return when (code) {
            400 -> "Некорректный запрос. Пожалуйста, попробуйте позже."
            401 -> "Ошибка авторизации. Проверьте правильность введенных данных для входа."
            403 -> "Доступ запрещен. Убедитесь, что у вас есть необходимые права."
            404 -> "Задачи не найдены. Попробуйте повторить позже."
            500 -> "Внутренняя ошибка сервера. Попробуйте повторить позже."
            else -> "Неизвестная ошибка при получении задач, код ответа: $code. Попробуйте повторить позже."
        }
    }
}