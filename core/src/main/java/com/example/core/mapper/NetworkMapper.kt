package com.example.core.mapper

interface NetworkMapper<FROM, TO> {

    fun map(from: TO): FROM

    fun map(list: Collection<TO>): List<FROM> {
        val result = ArrayList<FROM>()
        list.mapTo(result) { map(it) }
        return result
    }
}
