package de.nebulit.domain.commands.registerexpense

import de.nebulit.common.Command
import org.axonframework.modelling.command.TargetAggregateIdentifier

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619405397299
*/
data class RegisterExpenseCommand(
    @TargetAggregateIdentifier var expenseId: String,
    var documentId: String,
    var amount: String
) : Command
