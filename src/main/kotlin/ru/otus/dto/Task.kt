package ru.otus.dto

data class Task(
    var id: Int? = null,
    val name: String,
    var priority: Priority,
    var completed: Boolean? = null
)


enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}