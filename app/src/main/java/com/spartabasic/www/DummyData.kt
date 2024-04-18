package com.spartabasic.www

val myRecords = buildList {
    repeat(10){
        add(Record(record = "record $it"))
    }
}