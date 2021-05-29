package com.example.pokedex.util

object Util {

    fun returnId(id: Int): String {
        if (id / 10 == 0) {
            return "00$id"
        }

        if (id / 100 == 0) {
            return "0$id"
        }

        return id.toString()
    }

    fun capitalizeFirstLetter(name: String): String {
        return name.substring(0, 1).toUpperCase() + name.substring(1)
    }
}