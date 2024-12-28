package com.example.database3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.database3.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    // Объявляем объект базы данных
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем поведение окна так, чтобы система управляла отступами интерфейса
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Устанавливаем макет активности
        setContentView(R.layout.activity_main)

        // Настраиваем отступы для основного View, учитывая размеры системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализируем объект базы данных с использованием Singleton метода
        database = AppDatabase.getDatabase(this)

        // Инициализация элементов пользовательского интерфейса
        val useCoefficients = findViewById<Switch>(R.id.use_coefficients) // Переключатель
        val name = findViewById<EditText>(R.id.name) // Поле ввода имени
        val hours = findViewById<EditText>(R.id.hours) // Поле ввода количества часов
        val comment = findViewById<EditText>(R.id.comment) // Поле ввода комментария
        val submitButton = findViewById<Button>(R.id.submit_button) // Кнопка "Отправить"

        // Обработчик нажатия на кнопку
        submitButton.setOnClickListener {
            // Считываем данные из полей интерфейса
            val useCoeff = useCoefficients.isChecked // Состояние переключателя
            val userName = name.text.toString() // Имя пользователя
            val userHours = hours.text.toString().toIntOrNull() ?: 0 // Часы (с проверкой на null)
            val userComment = comment.text.toString() // Комментарий

            // Используем lifecycleScope для запуска корутины
            lifecycleScope.launch {
                // Выполняем вставку данных в базу на IO потоке (фоновая операция)
                withContext(Dispatchers.IO) {
                    val userData = UserData(0, useCoeff, userName, userHours, userComment) // Создаем объект данных
                    database.userDataDao().insert(userData) // Вставляем данные в базу
                }

                // Показываем Toast с информацией на главном потоке
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Коэффициенты: $useCoeff\nИмя: $userName\nЧасов: $userHours\nКомментарий: $userComment",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
