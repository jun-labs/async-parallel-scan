package project.alarm.app.test

import jakarta.annotation.PostConstruct
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.metamodel.Metamodel
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors.toUnmodifiableSet

@Component
class DatabaseInitialization {
    @PersistenceContext
    private lateinit var entityManager: EntityManager
    private lateinit var tableNames: Set<String>

    @PostConstruct
    fun afterPropertiesSet() {
        val metamodel: Metamodel = entityManager.metamodel
        val tableNames = metamodel.entities.stream()
            .filter {
                it.javaType.getAnnotation(Entity::class.java) != null
            }
            .map {
                it.name.lowercase(Locale.getDefault())
            }
            .collect(toUnmodifiableSet())
        this.tableNames = tableNames
    }

    @Transactional
    fun truncateAllEntity() {
        entityManager.flush()
        entityManager.clear()

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate()

        for (tableName: String in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate()
    }
}
