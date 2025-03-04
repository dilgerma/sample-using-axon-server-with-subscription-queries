package de.nebulit.domain.commands.classify

import de.nebulit.common.Command
import org.axonframework.modelling.command.TargetAggregateIdentifier

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619462423974
*/
data class ClassifyCommand(
    @TargetAggregateIdentifier var expenseId: String,
    var classification: String
) : Command
