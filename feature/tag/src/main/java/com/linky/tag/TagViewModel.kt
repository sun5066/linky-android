package com.linky.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.linky.data.usecase.tag.GetTagsUseCase
import com.linky.tag.state.TagSideEffect
import com.linky.tag.state.TagState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
) : ContainerHost<TagState, TagSideEffect>, ViewModel() {

    override val container = container<TagState, TagSideEffect>(TagState.Init)

    private fun getTags() {
        intent {
            val tags = getTagsUseCase
                .invoke()
                .cachedIn(viewModelScope)

            reduce { state.copy(tags = tags) }
        }
    }

    init {
        getTags()
    }

}