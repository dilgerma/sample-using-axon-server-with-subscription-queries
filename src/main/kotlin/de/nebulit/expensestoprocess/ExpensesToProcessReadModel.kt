package de.nebulit.expensestoprocess

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

data class ExpensesToProcessReadModelQuery(val expenseId: String)

class AllExpensesQuery()

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619405397453
*/
@Entity
class ExpensesToProcessReadModelEntity {
  @Id @Column(name = "expenseId") var expenseId: String? = null

  @Column(name = "classification") var classification: String? = null

  @Column(name = "status") var status: String? = null
}

data class ExpensesToProcessReadModel(val data: ExpensesToProcessReadModelEntity)
