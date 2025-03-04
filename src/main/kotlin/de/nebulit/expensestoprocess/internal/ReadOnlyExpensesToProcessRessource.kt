package de.nebulit.expensestoprocess.internal

import de.nebulit.expensestoprocess.ExpensesToProcessReadModel
import de.nebulit.expensestoprocess.ExpensesToProcessReadModelQuery
import java.util.concurrent.CompletableFuture
import mu.KotlinLogging
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619405397453
*/
@RestController
class ExpensestoprocessRessource(private var queryGateway: QueryGateway) {

  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @GetMapping("/expensestoprocess/{id}")
  fun findReadModel(
      @PathVariable("id") expenseId: String
  ): CompletableFuture<ExpensesToProcessReadModel> {
    return queryGateway.query(
        ExpensesToProcessReadModelQuery(expenseId), ExpensesToProcessReadModel::class.java)
  }
}
