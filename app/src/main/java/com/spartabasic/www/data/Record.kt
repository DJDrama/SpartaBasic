package com.spartabasic.www.data

data class Record(
    val trial: Int = 0,
    val target: Int,
    val record: Int,
    // enum
    val isCorrect: AnswerType,

    // sealed
    // val isCorrect: AnotherAnswerType,
)


enum class AnswerType(val answerValue: Int, val text: String) {
    CORRECT(answerValue = 1, text = "Correct!"),
    WRONG(answerValue = 0, text = "Wrong!")
}

sealed interface AnotherAnswerType {
    data class Correct(val answerValue: Int = 1, val text: String = "Correct!") : AnotherAnswerType

    data class Wrong(
        val answerValue: Int = 0,
        val text: String = "Wrong!",
        val stupidLevel: Int
    ) : AnotherAnswerType

}