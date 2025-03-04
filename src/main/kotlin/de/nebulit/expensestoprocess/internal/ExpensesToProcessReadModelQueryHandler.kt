package de.nebulit.expensestoprocess.internal

import de.nebulit.expensestoprocess.AllExpensesQuery
import de.nebulit.expensestoprocess.ExpensesToProcessReadModel
import de.nebulit.expensestoprocess.ExpensesToProcessReadModelQuery
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619405397453
*/
@Component
class ExpensesToProcessReadModelQueryHandler(
    private val repository: ExpensesToProcessReadModelRepository
) {

  @QueryHandler
  fun handleQuery(query: ExpensesToProcessReadModelQuery): ExpensesToProcessReadModel? {

    if (!repository.existsById(query.expenseId)) {
      return null
    }
    return ExpensesToProcessReadModel(repository.findById(query.expenseId).get())
  }

  @QueryHandler
  fun handleALlExpenses(query: AllExpensesQuery): List<ExpensesToProcessReadModel> {
    return repository.findAll().map { ExpensesToProcessReadModel(it) }
  }
}
