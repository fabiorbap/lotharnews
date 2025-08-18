package br.fabiorbap.lotharnews.common.util

import java.util.UUID

fun getNewId(): String {
    return UUID.randomUUID().toString()
}