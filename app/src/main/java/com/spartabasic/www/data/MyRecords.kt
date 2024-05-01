package com.spartabasic.www.data


object MyRecords {

    private var items = listOf<Record>()

    fun getItems() = items

    fun addRecord(record: Record) {
        // creates a new list
        items += record.copy(trial = items.size + 1)
    }

    fun isEmpty() = items.isEmpty()
}