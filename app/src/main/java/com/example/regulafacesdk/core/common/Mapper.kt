package com.example.regulafacesdk.core.common

interface Mapper<I,O> {
    fun map(input: I): O
    /*
    fun mapList(input: List<I>): List<O> {
        return input.map { map(it) }
    }
     */
}