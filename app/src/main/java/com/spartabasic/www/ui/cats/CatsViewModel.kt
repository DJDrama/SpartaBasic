package com.spartabasic.www.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spartabasic.www.domain.model.Cat
import com.spartabasic.www.domain.usecase.FetchCatsUseCase
import com.spartabasic.www.ui.model.CatItem
import com.spartabasic.www.ui.model.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel
@Inject
constructor(
    fetchCatsUseCase: FetchCatsUseCase,
) : ViewModel() {
    private val sizeLimit = 20

    private val _uiState = MutableStateFlow(CatsUiState())
    val uiState = _uiState.asStateFlow()

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow

    private val _stateFlow = MutableStateFlow<String>("")
    val stateFlow = _stateFlow

    private val _channel = Channel<String>()
    val channel = _channel.receiveAsFlow()

    init {

        fetchCatsUseCase(limit = sizeLimit, page = 0, null)
            .onStart {
                _uiState.update {
                    it.copy(isLoading = true)
                }
            }.onCompletion {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }.onEach { fetchedCats ->
                if (fetchedCats.size < sizeLimit) {
                    _uiState.update { state ->
                        state.copy(isEnd = true)
                    }
                }
                _uiState.update { state ->
                    state.copy(
                       // cats = state.cats + fetchedCats.map(Cat::toPresentationModel)
                        cats = fetchedCats.map(Cat::toPresentationModel)
                    )
                }
            }.catch {
                _errorState.send(element = it.message ?: "Unknown Error")
            }.launchIn(viewModelScope)

    }

    fun selectRandomCat() {
        viewModelScope.launch {
            delay(3000)
            val randomId = uiState.value.cats.random().id
            //_sharedFlow.emit(value = uiState.value.cats.random().id)
            // _stateFlow.value =randomId
            _channel.send(element = randomId)
        }
    }
    /*    fun setRandomIdToEmptyString(){
            _stateFlow.value = ""
        }*/

    data class CatsUiState(
        val isLoading: Boolean = false,
        val cats: List<CatItem> = emptyList(),
        val isEnd: Boolean = false,
    )
}

