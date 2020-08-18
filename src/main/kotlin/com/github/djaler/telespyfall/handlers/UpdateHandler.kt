package com.github.djaler.telespyfall.handlers

import com.github.insanusmokrassar.TelegramBotAPI.types.update.abstracts.Update


interface UpdateHandler {
    suspend fun handleUpdate(update: Update): Boolean

    val updateType: String

    val order get() = 1
}
