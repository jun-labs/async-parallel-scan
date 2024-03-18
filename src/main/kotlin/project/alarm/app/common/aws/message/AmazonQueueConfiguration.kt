package project.alarm.app.common.aws.message

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.net.URI

@Configuration
class AmazonSNSConfiguration(
    @Value("\${cloud.aws.region.static}")
    private val region: String,

    @Value("\${cloud.aws.credentials.access-key}")
    private val accessKey: String,

    @Value("\${cloud.aws.credentials.secret-key}")
    private val secretKey: String,

    @Value("\${cloud.aws.sns.endpoint}")
    private val snsEndpoint: String,

    @Value("\${cloud.aws.sqs.endpoint}")
    private val sqsEndpoint: String
) {

    @Bean
    fun snsClient(): SnsClient {
        val awsCredentials = AwsBasicCredentials.create(accessKey, secretKey)
        val credentialsProvider = StaticCredentialsProvider.create(awsCredentials)
        return SnsClient.builder()
            .endpointOverride(URI.create(snsEndpoint))
            .credentialsProvider(credentialsProvider)
            .region(Region.of(region))
            .build()
    }

    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .endpointOverride(URI.create(sqsEndpoint))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .region(Region.of(region))
            .build()
    }
}
