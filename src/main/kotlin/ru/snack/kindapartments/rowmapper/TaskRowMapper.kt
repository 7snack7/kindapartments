package ru.snack.kindapartments.rowmapper

import org.springframework.jdbc.core.RowMapper
import ru.snack.kindapartments.model.Task
import ru.snack.kindapartments.model.TaskStatus
import java.sql.ResultSet

class TaskRowMapper : RowMapper<Task> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Task {

        return Task(
            id = rs.getLong("id"),
            title = rs.getString("title"),
            description = rs.getString("description"),
            status = TaskStatus.valueOf(rs.getString("status")),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
            updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
        )
    }
}