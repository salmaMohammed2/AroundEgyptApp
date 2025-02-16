package com.example.aroundegypt.presentation.viewModels

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aroundegypt.data.mapper.toExperienceList
import com.example.aroundegypt.data.mapper.toRecommendedExperienceList
import com.example.aroundegypt.domain.enum.ExperienceType
import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.response.LikeResponse
import com.example.aroundegypt.domain.usecase.AddRecentExperiencesToDatabaseUseCase
import com.example.aroundegypt.domain.usecase.AddRecommendedExperiencesToDatabaseUseCase
import com.example.aroundegypt.domain.usecase.DeleteRecentExperiencesToDatabaseUseCase
import com.example.aroundegypt.domain.usecase.DeleteRecommendedExperiencesToDatabaseUseCase
import com.example.aroundegypt.domain.usecase.GetLocalRecentExperiencesUseCase
import com.example.aroundegypt.domain.usecase.GetLocalRecommendedExperiencesUseCase
import com.example.aroundegypt.domain.usecase.GetRecentExperiencesUseCase
import com.example.aroundegypt.domain.usecase.GetRecommendedExperiencesUseCase
import com.example.aroundegypt.domain.usecase.LikeExperienceUseCase
import com.example.aroundegypt.domain.usecase.SearchExperienceUseCase
import com.example.aroundegypt.domain.usecase.UpdateRecentExperienceInDatabaseUseCase
import com.example.aroundegypt.domain.usecase.UpdateRecommendedExperienceInDatabaseUseCase
import com.example.aroundegypt.isNetworkStable
import com.example.aroundegypt.presentation.viewState.CommonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExperienceViewModel @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val getRecommendedExperiencesUseCase: GetRecommendedExperiencesUseCase,
    private val getRecentExperiencesUseCase: GetRecentExperiencesUseCase,
    private val searchExperienceUseCase: SearchExperienceUseCase,
    private val addRecentExperiencesToDatabaseUseCase: AddRecentExperiencesToDatabaseUseCase,
    private val addRecommendedExperiencesToDatabaseUseCase: AddRecommendedExperiencesToDatabaseUseCase,
    private val getLocalRecentExperiencesUseCase: GetLocalRecentExperiencesUseCase,
    private val getLocalRecommendedExperiencesUseCase: GetLocalRecommendedExperiencesUseCase,
    private val deleteRecentExperiencesToDatabaseUseCase: DeleteRecentExperiencesToDatabaseUseCase,
    private val deleteRecommendedExperiencesToDatabaseUseCase: DeleteRecommendedExperiencesToDatabaseUseCase,
    private val likeExperienceUseCase: LikeExperienceUseCase,
    private val updateRecentExperienceInDatabaseUseCase: UpdateRecentExperienceInDatabaseUseCase,
    private val updateRecommendedExperienceInDatabaseUseCase: UpdateRecommendedExperienceInDatabaseUseCase
) : ViewModel() {

    private val _getRecommendedExperienceStateFlow =
        MutableStateFlow<CommonViewState<List<Experience>>>(CommonViewState(isIdle = true))
    val getRecommendedExperienceStateFlow: StateFlow<CommonViewState<List<Experience>>> =
        _getRecommendedExperienceStateFlow

    private val _getRecentExperienceStateFlow =
        MutableStateFlow<CommonViewState<List<Experience>>>(CommonViewState(isIdle = true))
    val getRecentExperienceStateFlow: StateFlow<CommonViewState<List<Experience>>> =
        _getRecentExperienceStateFlow

    private val _searchExperienceStateFlow =
        MutableStateFlow<CommonViewState<List<Experience>>>(CommonViewState(isIdle = true))
    val searchExperienceStateFlow: StateFlow<CommonViewState<List<Experience>>> =
        _searchExperienceStateFlow

    private val _likeExperienceStateFlow =
        MutableStateFlow<CommonViewState<LikeResponse>>(CommonViewState(isIdle = true))
    val likeExperienceStateFlow: StateFlow<CommonViewState<LikeResponse>> =
        _likeExperienceStateFlow


    init {
        if (checkNetworkStability()) {
            getRecommendedExperience()
            getRecentExperience()
        } else {
            getLocalRecommendedExperience()
            getLocalRecentExperience()
        }

    }

    private fun checkNetworkStability(): Boolean {
        return isNetworkStable(connectivityManager)
    }

    fun getRecommendedExperience() {
        viewModelScope.launch {
            try {
                _getRecommendedExperienceStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getRecommendedExperiencesUseCase.execute()
                _getRecommendedExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()?.experiences
                    )
                )
                addRecommendedExperienceToDatabase()

            } catch (t: Throwable) {
                _getRecommendedExperienceStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    fun getRecentExperience() {
        viewModelScope.launch {
            try {
                _getRecentExperienceStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getRecentExperiencesUseCase.execute()
                _getRecentExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()?.experiences
                    )
                )
                addRecentExperienceToDatabase()
            } catch (t: Throwable) {
                _getRecentExperienceStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    private fun getLocalRecommendedExperience() {
        viewModelScope.launch {
            try {
                _getRecommendedExperienceStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getLocalRecommendedExperiencesUseCase.execute()
                _getRecommendedExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.toExperienceList()
                    )
                )

            } catch (t: Throwable) {
                _getRecommendedExperienceStateFlow.emit(
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
                _getRecentExperienceStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getLocalRecentExperiencesUseCase.execute()
                _getRecentExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response
                    )
                )

            } catch (t: Throwable) {
                _getRecentExperienceStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    fun searchOnExperience(searchedWord: String) {
        viewModelScope.launch {
            try {
                _searchExperienceStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = searchExperienceUseCase.execute(searchedWord)
                _searchExperienceStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()?.experiences
                    )
                )

            } catch (t: Throwable) {
                _searchExperienceStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    private fun addRecommendedExperienceToDatabase() {
        viewModelScope.launch {
            try {
                deleteRecommendedExperiencesToDatabaseUseCase.execute()
                addRecommendedExperiencesToDatabaseUseCase.execute(
                    _getRecommendedExperienceStateFlow.value.data?.toRecommendedExperienceList()
                        ?: emptyList()
                )
            } catch (_: Throwable) {
            }
        }
    }

    private fun addRecentExperienceToDatabase() {
        viewModelScope.launch {
            try {
                deleteRecentExperiencesToDatabaseUseCase.execute()
                addRecentExperiencesToDatabaseUseCase.execute(
                    _getRecentExperienceStateFlow.value.data
                        ?: emptyList()
                )
            } catch (_: Throwable) {
            }
        }
    }

    fun likeAnExperience(experienceId: String, experienceType: Int) {
        viewModelScope.launch {
            try {
                _likeExperienceStateFlow.emit(CommonViewState(isLoading = true))

                val response = likeExperienceUseCase.execute(experienceId)

                response.body()?.data?.let { newLikesCount ->
                    _likeExperienceStateFlow.emit(
                        CommonViewState(
                            isSuccess = true,
                            data = LikeResponse(experienceId, newLikesCount)
                        )
                    )
                    if (experienceType == ExperienceType.RECOMMENDED.type)
                        updateRecommendedExperienceInDatabaseUseCase.execute(
                            experienceId,
                            newLikesCount,
                            true
                        )
                    else
                        updateRecentExperienceInDatabaseUseCase.execute(
                            experienceId,
                            newLikesCount,
                            true
                        )
                } ?: throw Exception("Invalid response")

            } catch (t: Throwable) {
                _likeExperienceStateFlow.emit(CommonViewState(error = t))
            }
        }
    }
}