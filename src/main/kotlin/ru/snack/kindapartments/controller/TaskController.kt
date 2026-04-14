package ru.snack.kindapartments.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.snack.kindapartments.dto.CreateTaskRequest
import ru.snack.kindapartments.dto.PageResponse
import ru.snack.kindapartments.dto.TaskResponse
import ru.snack.kindapartments.dto.UpdateStatusRequest
import ru.snack.kindapartments.model.TaskStatus
import ru.snack.kindapartments.service.TaskService

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val service: TaskService
) {

    @PostMapping
    fun create(
        @Valid @RequestBody request: CreateTaskRequest
    ): Mono<TaskResponse> {
        return service.createTask(request)
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): Mono<TaskResponse> {
        return service.getTaskById(id)
    }

    @GetMapping
    fun getAll(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestParam(required = false) status: TaskStatus?
    ): Mono<PageResponse<TaskResponse>> {

        return service.getTasks(page, size, status)
    }

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateStatusRequest
    ): Mono<TaskResponse> {

        return service.updateStatus(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long): Mono<Void> {
        return service.deleteTask(id)
    }
}