package com.spartabasic.www.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spartabasic.www.data.AnswerType
import com.spartabasic.www.data.MyRecords
import com.spartabasic.www.data.Record
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ModernGuessViewModel : ViewModel() {
    private val TAG = "ModernGuessViewModel"
    private var job: Job? = null

    private val mainCoroutineScope = CoroutineScope(Dispatchers.Main)
    private val mainImmediateCoroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> = _counter

    private val _randomValue = MutableLiveData<Int>()
    val randomValue: LiveData<Int> = _randomValue

    private val _correctStatus = MutableLiveData<Boolean>()
    val correctStatus: LiveData<Boolean> = _correctStatus

    private var counterValue = 1
    private var isGameOver = false

    init {
        initializeAndStart()
    }

    fun initializeAndStart() {
        // To avoid boilerplate Nullable values
        // ex)  if(counter.value!=null && counter.value!! > 1){
        counterValue = 1
        _randomValue.value = (1..100).random()
        isGameOver = false
        startCounting()
    }

    private fun startCounting() {
        if (isGameOver) return
        if (job?.isActive == true)
            job?.cancel()
        job = mainCoroutineScope.launch {
            Log.i(TAG, "Inside the launch block!")
            while (counterValue <= 100) {
                _counter.value = counterValue
                delay(timeMillis = (100..500).random().toLong())
                if (counterValue == 100)
                    return@launch
                counterValue += 1
            }
        }
        Log.i(TAG, "After the launch block!")
    }

    fun checkAnswer() {
        isGameOver = true
        if (randomValue.value != null && randomValue.value == counterValue) {
            MyRecords.addRecord(
                record = Record(
                    target = randomValue.value!!,
                    record = counterValue,
                    // enum
                    isCorrect = AnswerType.CORRECT
                    // sealed
                    //  isCorrect = AnotherAnswerType.Correct()
                )
            )
            _correctStatus.value = true
        } else {
            MyRecords.addRecord(
                record = Record(
                    target = randomValue.value!!,
                    record = counterValue,
                    /// enum
                    isCorrect = AnswerType.WRONG

                    // sealed
                    //isCorrect = AnotherAnswerType.Wrong(stupidLevel = (1..5).random())
                )
            )
            _correctStatus.value = false
        }
        cancelCounting()
    }

    fun continueCounting() {
        startCounting()
    }

    fun cancelCounting() {
        job?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared")
        cancelCounting()
    }

}