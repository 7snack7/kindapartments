package ru.snack.kindapartments.mapper

import ru.snack.kindapartments.dto.TaskResponse
import ru.snack.kindapartments.model.Task

object TaskMapper {

    fun toResponse(task: Task): TaskResponse {

        return TaskResponse(
            id = task.id!!,
            title = task.title,
            description = task.description,
            status = task.status,
            createdAt = task.createdAt,
            updatedAt = task.updatedAt
        )
    }
}