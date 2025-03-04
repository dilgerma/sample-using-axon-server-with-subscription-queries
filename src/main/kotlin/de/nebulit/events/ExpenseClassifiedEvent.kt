package de.nebulit.events

import de.nebulit.common.Event

/*
Boardlink: https://miro.com/app/board/uXjVIW2qnU0=/?moveToWidget=3458764619404971192
*/
data class ExpenseClassifiedEvent(var expenseId: String, var classification: String) : Event
