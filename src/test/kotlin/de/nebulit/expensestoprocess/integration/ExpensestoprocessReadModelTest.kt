package de.nebulit.expensestoprocess.integration

import de.nebulit.common.support.BaseIntegrationTest
import de.nebulit.common.support.RandomData
import de.nebulit.common.support.awaitUntilAssserted
import de.nebulit.domain.commands.registerexpense.RegisterExpenseCommand
import de.nebulit.expensestoprocess.ExpensesToProcessReadModel
import de.nebulit.expensestoprocess.ExpensesToProcessReadModelQuery
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

/** Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619464818921 */
class ExpensestoprocessReadModelTest : BaseIntegrationTest() {

  @Autowired private lateinit var commandGateway: CommandGateway

  @Autowired private lateinit var queryGateway: QueryGateway

  @Test
  fun `Expensestoprocess Read Model Test`() {

    var registerExpenseCommand =
        RandomData.newInstance<RegisterExpenseCommand> {
          this.expenseId = UUID.randomUUID().toString()
        }
    commandGateway.sendAndWait<Any>(registerExpenseCommand)

    var anotherExpenseCommand =
        RandomData.newInstance<RegisterExpenseCommand> {
          this.expenseId = UUID.randomUUID().toString()
        }
    commandGateway.sendAndWait<Any>(registerExpenseCommand)
    commandGateway.sendAndWait<Any>(anotherExpenseCommand)

    awaitUntilAssserted {
      val result =
          queryGateway.query(
              ExpensesToProcessReadModelQuery(registerExpenseCommand.expenseId),
              ExpensesToProcessReadModel::class.java)
      assertThat(result.get().data.classification).isEqualTo("classified")
    }
  }
}
