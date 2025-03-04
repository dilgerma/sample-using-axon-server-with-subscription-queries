package de.nebulit.classify.internal

import de.nebulit.common.Processor
import de.nebulit.domain.commands.classify.ClassifyCommand
import de.nebulit.expensestoprocess.AllExpensesQuery
import de.nebulit.expensestoprocess.ExpensesToProcessReadModel
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619462595129
*/
@Component
class AutomationProcessor(val queryGateway: QueryGateway) :
    Processor, ApplicationListener<ApplicationReadyEvent> {
  var logger = KotlinLogging.logger {}

  @Autowired lateinit var commandGateway: CommandGateway

  override fun onApplicationEvent(event: ApplicationReadyEvent) {
    queryGateway
        .subscriptionQuery(
            AllExpensesQuery(),
            ResponseTypes.multipleInstancesOf(ExpensesToProcessReadModel::class.java),
            ResponseTypes.instanceOf(ExpensesToProcessReadModel::class.java),
        )
        .let { subscription ->
          subscription.initialResult().subscribe { result -> }

          subscription.updates().subscribe { update ->
            commandGateway.send<Any>(
                ClassifyCommand(expenseId = update.data.expenseId!!, classification = "classified"))
          }
        }
  }
}
