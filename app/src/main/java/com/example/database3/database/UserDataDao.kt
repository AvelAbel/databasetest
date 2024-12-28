package com.example.database3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database3.UserData
import kotlinx.coroutines.flow.Flow

// Аннотация @Dao (Data Access Object) указывает, что интерфейс используется для взаимодействия с базой данных.
// Она позволяет определять методы для выполнения операций с базой данных, таких как вставка, обновление, удаление и запросы.
@Dao
interface UserDataDao {

    // Метод для вставки объекта UserData в таблицу базы данных
    // Аннотация @Insert автоматически обрабатывает SQL-запрос для вставки
    @Insert
    fun insert(userData: UserData):Long

    // Метод для получения всех записей из таблицы user_data
    // Аннотация @Query позволяет вручную указать SQL-запрос
    @Query("SELECT * FROM user_data")
    fun getAll(): Flow<List<UserData>>

}
