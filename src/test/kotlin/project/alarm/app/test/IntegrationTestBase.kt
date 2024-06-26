package project.alarm.app.test

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
abstract class IntegrationTestBase {
    @Autowired
    lateinit var dbInitialization: DatabaseInitialization

    @BeforeEach
    fun setUp() {
        dbInitialization.truncateAllEntity()
    }
}
