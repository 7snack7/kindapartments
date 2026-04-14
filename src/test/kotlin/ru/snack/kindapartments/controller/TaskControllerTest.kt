package ru.snack.kindapartments.controller

import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import ru.snack.kindapartments.dto.TaskResponse
import ru.snack.kindapartments.model.TaskStatus
import ru.snack.kindapartments.service.TaskService
import java.time.LocalDateTime


@WebFluxTest(TaskController::class)
class TaskControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    lateinit var taskService: TaskService

    @Test
    fun `should return task by id`() {

        val response = TaskResponse(
            id = 1,
            title = "test",
            description = null,
            status = TaskStatus.NEW,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        whenever(taskService.getTaskById(1))
            .thenReturn(Mono.just(response))

        webTestClient.get()
            .uri("/api/tasks/1")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
    }
}