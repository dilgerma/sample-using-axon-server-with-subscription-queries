package de.nebulit.classify.internal

import de.nebulit.domain.commands.classify.ClassifyCommand
import java.util.concurrent.CompletableFuture
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class ClassifyPayload(var expenseId: String, var classification: String)

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619462423974
*/
@RestController
class ClassifyRessource(private var commandGateway: CommandGateway) {

  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @PostMapping("/debug/classify")
  fun processDebugCommand(
      @RequestParam expenseId: String,
      @RequestParam classification: String
  ): CompletableFuture<Any> {
    return commandGateway.send(ClassifyCommand(expenseId, classification))
  }

  @CrossOrigin
  @PostMapping("/classify/{id}")
  fun processCommand(
      @PathVariable("id") aggregateId: java.util.UUID,
      @RequestBody payload: ClassifyPayload
  ): CompletableFuture<Any> {
    return commandGateway.send(
        ClassifyCommand(expenseId = payload.expenseId, classification = payload.classification))
  }
}
