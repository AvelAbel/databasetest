package com.example.database3


import androidx.room.Entity
import androidx.room.PrimaryKey

// Указываем, что класс является сущностью (таблицей) в базе данных Room
@Entity(tableName = "user_data")
data class UserData(
    // Обозначаем первичный ключ таблицы, который будет автоматически генерироваться
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Поле для хранения флага, используется ли коэффициент
    val useCoefficients: Boolean,
    // Поле для хранения имени пользователя
    val name: String,
    // Поле для хранения количества часов (например, рабочего времени)
    val hours: Int,
    // Поле для хранения комментария, связанного с данными пользователя
    val comment: String
)
