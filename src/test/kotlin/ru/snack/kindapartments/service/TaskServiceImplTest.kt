package ru.snack.kindapartments.service

import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import reactor.test.StepVerifier
import ru.snack.kindapartments.model.Task
import ru.snack.kindapartments.model.TaskStatus
import ru.snack.kindapartments.repository.TaskRepository
import java.time.LocalDateTime

class TaskServiceImplTest {

    private val repository: TaskRepository = mock()
    private val service = TaskServiceImpl(repository)

    @Test
    fun `should return task by id`() {

        val task = Task(
            id = 1,
            title = "test",
            description = null,
            status = TaskStatus.NEW,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        whenever(repository.findById(1)).thenReturn(task)

        StepVerifier.create(service.getTaskById(1))
            .assertNext {
                assert(it.id == 1L)
                assert(it.title == "test")
            }
            .verifyComplete()

        verify(repository).findById(1)
    }
}