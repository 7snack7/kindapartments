package ru.snack.kindapartments.service

import reactor.core.publisher.Mono
import ru.snack.kindapartments.dto.CreateTaskRequest
import ru.snack.kindapartments.dto.PageResponse
import ru.snack.kindapartments.dto.TaskResponse
import ru.snack.kindapartments.dto.UpdateStatusRequest
import ru.snack.kindapartments.model.TaskStatus

interface TaskService {
    fun createTask(request: CreateTaskRequest): Mono<TaskResponse>
    fun getTaskById(id: Long): Mono<TaskResponse>
    fun getTasks(page: Int, size: Int, status: TaskStatus?): Mono<PageResponse<TaskResponse>>
    fun updateStatus(id: Long, request: UpdateStatusRequest): Mono<TaskResponse>
    fun deleteTask(id: Long): Mono<Void>
}