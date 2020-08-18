package com.github.djaler.telespyfall.handlers

import com.github.djaler.telespyfall.filters.query.CallbackQueryFilter
import com.github.djaler.telespyfall.utils.decodeCallbackData
import com.github.djaler.telespyfall.utils.isCallbackForHandler
import com.github.insanusmokrassar.TelegramBotAPI.types.CallbackQuery.MessageDataCallbackQuery
import com.github.insanusmokrassar.TelegramBotAPI.types.UPDATE_CALLBACK_QUERY
import com.github.insanusmokrassar.TelegramBotAPI.types.update.CallbackQueryUpdate
import com.github.insanusmokrassar.TelegramBotAPI.types.update.abstracts.Update

abstract class CallbackQueryHandler(private val filter: CallbackQueryFilter? = null) : UpdateHandler {
    override val updateType get() = UPDATE_CALLBACK_QUERY

    override suspend fun handleUpdate(update: Update): Boolean {
        if (update !is CallbackQueryUpdate) {
            return false
        }
        val callbackQuery = update.data as? MessageDataCallbackQuery ?: return false

        if (filter?.filter(callbackQuery) == false) {
            return false
        }

        if (!isCallbackForHandler(callbackQuery.data, javaClass)) {
            return false
        }

        val data = decodeCallbackData(callbackQuery.data, javaClass)

        handleCallback(callbackQuery, data)

        return true
    }

    protected abstract suspend fun handleCallback(query: MessageDataCallbackQuery, data: String)
}
