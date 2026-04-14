package ru.snack.kindapartments.repository

import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import ru.snack.kindapartments.model.Task
import ru.snack.kindapartments.model.TaskStatus
import ru.snack.kindapartments.rowmapper.TaskRowMapper
import java.time.LocalDateTime

@Repository
class TaskRepository(
    private val jdbcClient: JdbcClient
) {

    private val rowMapper = TaskRowMapper()

    fun save(task: Task): Task {

        val sql = """
            INSERT INTO tasks(title, description, status, created_at, updated_at)
            VALUES (:title, :description, :status, :createdAt, :updatedAt)
            RETURNING *
        """

        return jdbcClient.sql(sql)
            .param("title", task.title)
            .param("description", task.description)
            .param("status", task.status.name)
            .param("createdAt", task.createdAt)
            .param("updatedAt", task.updatedAt)
            .query(rowMapper)
            .single()
    }

    fun findById(id: Long): Task? {
        return jdbcClient.sql("SELECT * FROM tasks WHERE id = :id")
            .param("id", id)
            .query(rowMapper)
            .optional()
            .orElse(null)
    }

    fun deleteById(id: Long) {
        jdbcClient.sql("DELETE FROM tasks WHERE id = :id")
            .param("id", id)
            .update()
    }

    fun updateStatus(
        id: Long,
        status: TaskStatus,
        updatedAt: LocalDateTime
    ): Task? {

        val sql = """
            UPDATE tasks
            SET status = :status,
                updated_at = :updatedAt
            WHERE id = :id
            RETURNING *
        """

        return jdbcClient.sql(sql)
            .param("id", id)
            .param("status", status.name)
            .param("updatedAt", updatedAt)
            .query(rowMapper)
            .optional()
            .orElse(null)
    }

    fun findAll(
        page: Int,
        size: Int,
        status: TaskStatus?
    ): Pair<List<Task>, Long> {

        val offset = page * size

        val filter = if (status != null) "WHERE status = :status" else ""

        val query = """
            SELECT * FROM tasks
            $filter
            ORDER BY created_at DESC
            LIMIT :size OFFSET :offset
        """

        val tasks = jdbcClient.sql(query)
            .param("status", status?.name)
            .param("size", size)
            .param("offset", offset)
            .query(rowMapper)
            .list()

        val countQuery = """
            SELECT count(*) FROM tasks $filter
        """

        val total = jdbcClient.sql(countQuery)
            .param("status", status?.name)
            .query(Long::class.java)
            .single()

        return tasks to total
    }
}