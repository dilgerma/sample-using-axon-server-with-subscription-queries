package de.nebulit

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import java.time.Duration
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

object ApplicationStarter {
  @JvmStatic
  fun main(args: Array<String>) {
    SpringApplication.from(SpringApp::main).with(ContainerConfiguration::class.java).run(*args)
  }
}

@TestConfiguration(proxyBeanMethods = false)
internal class ContainerConfiguration {

  companion object {
    private val axonServer =
        GenericContainer(DockerImageName.parse("axoniq/axonserver:latest"))
            .withExposedPorts(8024, 8124)
            .withCreateContainerCmdModifier { cmd ->
              cmd.withHostConfig(
                  cmd.hostConfig?.withPortBindings(
                      PortBinding(Ports.Binding.bindPort(8024), ExposedPort(8024)),
                      PortBinding(Ports.Binding.bindPort(8124), ExposedPort(8124))))
            }
            .waitingFor(
                Wait.forHttp("/actuator/health")
                    .forPort(8024)
                    .forStatusCode(200)
                    .withStartupTimeout(Duration.ofSeconds(30)))
            .withEnv("AXONIQ_AXONSERVER_STANDALONE", "true")
            .withReuse(true)

    val POSTGRES_PORT = 5432

    @JvmStatic
    @DynamicPropertySource
    fun configureProperties(registry: DynamicPropertyRegistry) {
      axonServer.start()
      registry.add("axon.axonserver.servers") { "localhost:8124" }
    }
  }

  @Bean
  @ServiceConnection
  fun postgresContainer(): PostgreSQLContainer<*> {
    val postgres =
        PostgreSQLContainer(DockerImageName.parse("postgres"))
            .withReuse(true)
            .withExposedPorts(POSTGRES_PORT)
            .withPassword("postgres")
            .withUsername("postgres")
    return postgres
  }

  @Bean fun axonServerContainer() = axonServer
}
