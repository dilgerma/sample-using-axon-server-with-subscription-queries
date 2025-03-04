package de.nebulit.domain

import de.nebulit.domain.commands.classify.ClassifyCommand
import de.nebulit.domain.commands.registerexpense.RegisterExpenseCommand
import de.nebulit.events.ExpenseClassifiedEvent
import de.nebulit.events.ExpenseRegisteredEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class ExpenseAggregate {

  @AggregateIdentifier var expenseId: String? = null

  @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
  @CommandHandler
  fun handle(command: RegisterExpenseCommand) {

    AggregateLifecycle.apply(
        ExpenseRegisteredEvent(
            expenseId = command.expenseId,
            documentId = command.documentId,
            amount = command.amount))
  }

  @EventSourcingHandler
  fun on(event: ExpenseRegisteredEvent) {
    // handle event
    expenseId = event.expenseId
  }

  @CommandHandler
  fun handle(command: ClassifyCommand) {

    AggregateLifecycle.apply(
        ExpenseClassifiedEvent(
            expenseId = command.expenseId, classification = command.classification))
  }

  @EventSourcingHandler
  fun on(event: ExpenseClassifiedEvent) {
    // handle event
    expenseId = event.expenseId
  }
}
