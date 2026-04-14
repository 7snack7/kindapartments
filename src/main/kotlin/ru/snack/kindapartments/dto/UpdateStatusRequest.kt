package ru.snack.kindapartments.dto

import ru.snack.kindapartments.model.TaskStatus

data class UpdateStatusRequest(
    val status: TaskStatus
)