package project.alarm.app.core.user.persistence

import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import project.alarm.app.core.user.domain.User
import java.sql.PreparedStatement

@Repository
class UserDao(
    private val jdbcTemplate: JdbcTemplate,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) {

    fun findMaxUserId(): Long? {
        val sql = "SELECT id FROM user ORDER BY id DESC LIMIT 1"
        return jdbcTemplate.queryForObject(sql, Long::class.java)
    }

    fun findByIdIn(ids: List<Long>): List<Long> {
        val sql = "SELECT id FROM user WHERE id IN (:ids) AND alarm = true"
        val paramSource = MapSqlParameterSource()
        paramSource.addValue("ids", ids)
        return namedParameterJdbcTemplate.query(sql, paramSource) { rs, _ ->
            rs.getLong("id")
        }
    }

    fun bulkInsertUsers(users: List<User>) {
        val sql = "INSERT INTO user (name, alarm) VALUES (?, ?)"
        jdbcTemplate.batchUpdate(sql, object : BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                val user = users[i]
                ps.setString(1, user.name)
                ps.setBoolean(2, user.alarm)
            }

            override fun getBatchSize(): Int = users.size
        })
    }
}
