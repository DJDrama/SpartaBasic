package com.spartabasic.www.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spartabasic.www.domain.model.Cat
import com.spartabasic.www.domain.usecase.FetchCatsUseCase
import com.spartabasic.www.ui.model.CatItem
import com.spartabasic.www.ui.model.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CatsViewModel
@Inject
constructor(
    fetchCatsUseCase: FetchCatsUseCase
) : ViewModel() {
    private val sizeLimit = 20

    private val _uiState = MutableStateFlow(CatsUiState())
    val uiState = _uiState.asStateFlow()

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

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
                        cats = state.cats + fetchedCats.map(Cat::toPresentationModel)
                    )
                }
            }.catch {
                _errorState.send(element = it.message ?: "Unknown Error")
            }.launchIn(viewModelScope)
    }

    data class CatsUiState(
        val isLoading: Boolean = false,
        val cats: List<CatItem> = emptyList(),
        val isEnd: Boolean = false,
    )
}

