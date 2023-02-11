package ru.otus

import com.google.gson.reflect.TypeToken
import okhttp3.Response
import ru.otus.dto.Task

class TaskHttpClient: BaseHttpClient() {
    private val url = "http://localhost:8080/api/v1/tasks"

    fun createTask(task: Task): Task? {
        val rs = doPostRequest(url, task).body?.string()
        return rs?.let { gson.fromJson<Task>(it) }
    }

    fun deleteTask(id: Int, task: Task): Task? {
        val rs = doDeleteRequest(url, id, task).body?.string()

        return rs?.let { gson.fromJson<Task>(it) }
    }

    fun changeTask(id: Int, task: Task, status: Boolean): Task? {
        val rs = doPutRequest(url, id, task, status).body?.string()
        return rs?.let { gson.fromJson<Task>(it) }
    }

    fun getTasks(task: Task, status: Boolean): List<Task>? {
        val rs = doGetRequest(url, task, status).body?.string()
        println("When call GetTask, rs = $rs")

        val type = object : TypeToken<List<Task>>() {}.type
        val result: List<Task>? = rs?.let { gson.fromJsonArray<List<Task>>(json = it, typeToken = type) }
        println("When call GetTask, result = $result")

        return return result
    }

    fun createTaskError(task: Task): Response? {
        return   doPostRequest(url, task)
    }

    fun deleteTaskError(id: Int, task: Task): Response? {
        return   doDeleteRequest(url, id, task)
    }

    fun changeTaskError(id: Int, task: Task, status: Boolean): Response? {
        return   doPutRequest(url, id, task, status)
    }
}