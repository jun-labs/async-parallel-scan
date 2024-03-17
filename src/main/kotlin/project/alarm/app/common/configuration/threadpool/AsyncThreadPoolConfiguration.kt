package project.alarm.app.common.configuration.threadpool

import java.util.concurrent.Executor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class AsyncThreadPoolConfiguration : AsyncConfigurer {

    @Bean(name = ["asyncThreadPool"])
    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
        corePoolSize = 50
        maxPoolSize = 200
        queueCapacity = 500
        initialize()
        }
    }
}
