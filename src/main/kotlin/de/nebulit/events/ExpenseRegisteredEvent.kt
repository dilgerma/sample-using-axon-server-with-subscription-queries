package de.nebulit.events

import de.nebulit.common.Event

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619404971083
*/
data class ExpenseRegisteredEvent(
    var expenseId: String,
    var amount: String,
    var documentId: String
) : Event
