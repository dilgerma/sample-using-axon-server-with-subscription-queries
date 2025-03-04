package de.nebulit.expensestoprocess.internal

import de.nebulit.events.ExpenseClassifiedEvent
import de.nebulit.events.ExpenseRegisteredEvent
import de.nebulit.expensestoprocess.AllExpensesQuery
import de.nebulit.expensestoprocess.ExpensesToProcessReadModel
import de.nebulit.expensestoprocess.ExpensesToProcessReadModelEntity
import jakarta.annotation.PostConstruct
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface ExpensesToProcessReadModelRepository :
    JpaRepository<ExpensesToProcessReadModelEntity, String>

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619405397453
*/
@Component
class ExpensesToProcessReadModelProjector(
    var repository: ExpensesToProcessReadModelRepository,
    var queryGateway: QueryGateway,
    var queryEmitter: QueryUpdateEmitter
) : InitializingBean {

  @EventHandler
  fun on(event: ExpenseRegisteredEvent) {
    // throws exception if not available (adjust logic)
    val entity =
        this.repository.findById(event.expenseId).orElse(ExpensesToProcessReadModelEntity())
    entity.apply { expenseId = event.expenseId }.also { this.repository.save(it) }
    this.queryEmitter.emit(
        AllExpensesQuery::class.java, { true }, ExpensesToProcessReadModel(entity))
  }

  @EventHandler
  fun on(event: ExpenseClassifiedEvent) {
    // throws exception if not available (adjust logic)
    val entity =
        this.repository.findById(event.expenseId).orElse(ExpensesToProcessReadModelEntity())
    entity
        .apply {
          expenseId = event.expenseId
          classification = event.classification
        }
        .also { this.repository.save(it) }
  }

  @PostConstruct fun init() {}

  override fun afterPropertiesSet() {}
}
