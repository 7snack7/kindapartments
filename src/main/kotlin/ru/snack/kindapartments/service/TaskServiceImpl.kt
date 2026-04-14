package ru.snack.kindapartments.service

import ru.snack.kindapartments.dto.CreateTaskRequest
import ru.snack.kindapartments.dto.PageResponse
import ru.snack.kindapartments.dto.TaskResponse
import ru.snack.kindapartments.dto.UpdateStatusRequest
import ru.snack.kindapartments.exception.NotFoundException
import ru.snack.kindapartments.mapper.TaskMapper
import ru.snack.kindapartments.model.Task
import ru.snack.kindapartments.model.TaskStatus
import ru.snack.kindapartments.repository.TaskRepository

import org.springframework.stereotype.Service

import reactor.core.publisher.Mono

import java.time.LocalDateTime
import kotlin.math.ceil


@Service
class TaskServiceImpl(
    private val repository: TaskRepository
) : TaskService {

    override fun createTask(request: CreateTaskRequest): Mono<TaskResponse> {

        return Mono.fromCallable {

            val now = LocalDateTime.now()

            val task = Task(
                id = null,
                title = request.title,
                description = request.description,
                status = TaskStatus.NEW,
                createdAt = now,
                updatedAt = now
            )

            repository.save(task)

        }.map { TaskMapper.toResponse(it) }
    }

    override fun getTaskById(id: Long): Mono<TaskResponse> {

        return Mono.fromCallable {

            repository.findById(id)
                ?: throw NotFoundException("Task not found")

        }.map { TaskMapper.toResponse(it) }
    }

    override fun getTasks(
        page: Int,
        size: Int,
        status: TaskStatus?
    ): Mono<PageResponse<TaskResponse>> {

        return Mono.fromCallable {

            val (tasks, total) = repository.findAll(page, size, status)

            PageResponse(
                content = tasks.map { TaskMapper.toResponse(it) },
                page = page,
                size = size,
                totalElements = total,
                totalPages = ceil(total / size.toDouble()).toInt()
            )
        }
    }

    override fun updateStatus(
        id: Long,
        request: UpdateStatusRequest
    ): Mono<TaskResponse> {

        return Mono.fromCallable {

            repository.updateStatus(
                id,
                request.status,
                LocalDateTime.now()
            ) ?: throw NotFoundException("Task not found")

        }.map { TaskMapper.toResponse(it) }
    }

    override fun deleteTask(id: Long): Mono<Void> {
        return Mono.fromSupplier {
            repository.deleteById(id)
        }.then()
    }
}