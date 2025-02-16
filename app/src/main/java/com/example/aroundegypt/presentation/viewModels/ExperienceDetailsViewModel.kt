package com.example.aroundegypt.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aroundegypt.data.mapper.toExperienceList
import com.example.aroundegypt.domain.enum.ExperienceType
import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.response.LikeResponse
import com.example.aroundegypt.domain.usecase.GetLocalRecentExperiencesUseCase
import com.example.aroundegypt.domain.usecase.GetLocalRecommendedExperiencesUseCase
import com.example.aroundegypt.domain.usecase.LikeExperienceUseCase
import com.example.aroundegypt.domain.usecase.UpdateRecentExperienceInDatabaseUseCase
import com.example.aroundegypt.domain.usecase.UpdateRecommendedExperienceInDatabaseUseCase
import com.example.aroundegypt.presentation.viewState.CommonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExperienceDetailsViewModel @Inject constructor(
    private val getLocalRecentExperiencesUseCase: GetLocalRecentExperiencesUseCase,
    private val getLocalRecommendedExperiencesUseCase: GetLocalRecommendedExperiencesUseCase,
    private val likeExperienceUseCase: LikeExperienceUseCase,
    private val updateRecentExperienceInDatabaseUseCase: UpdateRecentExperienceInDatabaseUseCase,
    private val updateRecommendedExperienceInDatabaseUseCase: UpdateRecommendedExperienceInDatabaseUseCase
) : ViewModel() {

    private val _getLocalRecommendedExperienceStateFlow =
        MutableStateFlow<CommonViewState<List<Experience>>>(CommonViewState(isIdle = true))
    val getLocalRecommendedExperienceStateFlow: StateFlow<CommonViewState<List<Experience>>> =
        _getLocalRecommendedExperienceStateFlow

    private val _getLocalRecentExperienceStateFlow =
        MutableStateFlow<CommonViewState<List<Experience>>>(CommonViewState(isIdle = true))
    val getLocalRecentExperienceStateFlow: StateFlow<CommonViewState<List<Experience>>> =
        _getLocalRecentExperienceStateFlow

    private val _likeExperienceStateFlow =
        MutableStateFlow<CommonViewState<LikeResponse>>(CommonViewState(isIdle = true))
    val likeExperienceStateFlow: StateFlow<CommonViewState<LikeResponse>> = _likeExperienceStateFlow

    init {
        getLocalRecommendedExperience()
        getLocalRecentExperience()
    }

    private fun getLocalRecommendedExperience() {
        viewModelScope.launch {
            try {
                _getLocalRecommendedExperienceStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getLocalRecommendedExperiencesUseCase.execute()
                _getLocalRecommendedExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true, data = response.toExperienceList()
                    )
                )

            } catch (t: Throwable) {
                _getLocalRecommendedExperienceStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    private fun getLocalRecentExperience() {
        viewModelScope.launch {
            try {
                _getLocalRecentExperienceStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getLocalRecentExperiencesUseCase.execute()
                _getLocalRecentExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true, data = response
                    )
                )

            } catch (t: Throwable) {
                _getLocalRecentExperienceStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    private fun getRecommendedExperienceById(id: String): Experience? {
        return getLocalRecommendedExperienceStateFlow.value.data?.find { it.id == id }
    }

    private fun getRecentExperienceById(id: String): Experience? {
        return getLocalRecentExperienceStateFlow.value.data?.find { it.id == id }
    }

    fun getExperienceById(id: String, type: Int): Experience? {
        return if (type == ExperienceType.RECOMMENDED.type) getRecommendedExperienceById(id)
        else getRecentExperienceById(id)
    }

    fun likeAnExperience(experienceId: String, experienceType: Int) {
        viewModelScope.launch {
            try {
                _likeExperienceStateFlow.emit(CommonViewState(isLoading = true))

                val response = likeExperienceUseCase.execute(experienceId)
                _likeExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true, data = response.body()
                    )
                )

                if (experienceType == ExperienceType.RECOMMENDED.type) response.body()?.data?.let {
                    updateRecommendedExperienceInDatabaseUseCase.execute(
                        experienceId, it, true
                    )
                }
                else response.body()?.data?.let {
                    updateRecentExperienceInDatabaseUseCase.execute(
                        experienceId, it, true
                    )
                }

            } catch (t: Throwable) {
                _likeExperienceStateFlow.emit(CommonViewState(error = t))
            }
        }
    }

}