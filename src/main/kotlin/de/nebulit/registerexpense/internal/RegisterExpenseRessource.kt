package de.nebulit.registerexpense.internal

import de.nebulit.domain.commands.registerexpense.RegisterExpenseCommand
import java.util.concurrent.CompletableFuture
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class RegisterExpensePayload(
    var expenseId: String,
    var documentId: String,
    var amount: String
)

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619405397299
*/
@RestController
class RegisterExpenseRessource(private var commandGateway: CommandGateway) {

  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @PostMapping("/debug/registerexpense")
  fun processDebugCommand(
      @RequestParam expenseId: String,
      @RequestParam documentId: String,
      @RequestParam amount: String
  ): CompletableFuture<Any> {
    return commandGateway.send(RegisterExpenseCommand(expenseId, documentId, amount))
  }

  @CrossOrigin
  @PostMapping("/registerexpense/{id}")
  fun processCommand(
      @PathVariable("id") aggregateId: java.util.UUID,
      @RequestBody payload: RegisterExpensePayload
  ): CompletableFuture<Any> {
    return commandGateway.send(
        RegisterExpenseCommand(
            expenseId = payload.expenseId,
            documentId = payload.documentId,
            amount = payload.amount))
  }
}
