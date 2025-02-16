package com.example.aroundegypt.presentation.viewModels

import android.net.ConnectivityManager
import android.net.Network
import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.domain.response.ExperienceResponse
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
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ExperienceViewModelTest {
    private lateinit var experienceViewModel: ExperienceViewModel
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var getRecommendedExperiencesUseCase: GetRecommendedExperiencesUseCase
    private lateinit var getRecentExperiencesUseCase: GetRecentExperiencesUseCase
    private lateinit var searchExperienceUseCase: SearchExperienceUseCase
    private lateinit var addRecentExperiencesToDatabaseUseCase: AddRecentExperiencesToDatabaseUseCase
    private lateinit var addRecommendedExperiencesToDatabaseUseCase: AddRecommendedExperiencesToDatabaseUseCase
    private lateinit var getLocalRecentExperiencesUseCase: GetLocalRecentExperiencesUseCase
    private lateinit var getLocalRecommendedExperiencesUseCase: GetLocalRecommendedExperiencesUseCase
    private lateinit var deleteRecentExperiencesToDatabaseUseCase: DeleteRecentExperiencesToDatabaseUseCase
    private lateinit var deleteRecommendedExperiencesToDatabaseUseCase: DeleteRecommendedExperiencesToDatabaseUseCase
    private lateinit var likeExperienceUseCase: LikeExperienceUseCase
    private lateinit var updateRecentExperienceInDatabaseUseCase: UpdateRecentExperienceInDatabaseUseCase
    private lateinit var updateRecommendedExperienceInDatabaseUseCase: UpdateRecommendedExperienceInDatabaseUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        connectivityManager = mockk(relaxed = true)
        val mockNetwork = mockk<Network>()
        every { connectivityManager.activeNetwork } returns mockNetwork

        getRecentExperiencesUseCase = mockk()
        getRecommendedExperiencesUseCase = mockk()
        searchExperienceUseCase = mockk()
        addRecentExperiencesToDatabaseUseCase = mockk()
        addRecommendedExperiencesToDatabaseUseCase = mockk()
        getLocalRecentExperiencesUseCase = mockk()
        getLocalRecommendedExperiencesUseCase = mockk()
        deleteRecentExperiencesToDatabaseUseCase = mockk()
        deleteRecommendedExperiencesToDatabaseUseCase = mockk()
        likeExperienceUseCase = mockk()
        updateRecentExperienceInDatabaseUseCase = mockk()
        updateRecommendedExperienceInDatabaseUseCase = mockk()
        experienceViewModel = ExperienceViewModel(
            connectivityManager,
            getRecommendedExperiencesUseCase,
            getRecentExperiencesUseCase,
            searchExperienceUseCase,
            addRecentExperiencesToDatabaseUseCase,
            addRecommendedExperiencesToDatabaseUseCase,
            getLocalRecentExperiencesUseCase,
            getLocalRecommendedExperiencesUseCase,
            deleteRecentExperiencesToDatabaseUseCase,
            deleteRecommendedExperiencesToDatabaseUseCase,
            likeExperienceUseCase,
            updateRecentExperienceInDatabaseUseCase,
            updateRecommendedExperienceInDatabaseUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getRecommendedExperiences_succeed_whenReturnsDataNotEqualNull() = runTest {
        val mockResults = listOf(
            Experience(id = "1", title = "Abu Simbel Temples"),
            Experience(id = "2", title = "Kom Ombo Temple")
        )

        val mockApiResponse = Response.success(
            ExperienceResponse(
                mockResults
            )
        )

        coEvery { getRecommendedExperiencesUseCase.execute() } returns mockApiResponse

        experienceViewModel.getRecommendedExperience()

        assert(experienceViewModel.getRecommendedExperienceStateFlow.value.data?.get(0)?.title == "Abu Simbel Temples")
    }

    @Test
    fun getRecentExperiences_succeed_whenReturnsDataNotEqualNull() = runTest {
        val mockResults = listOf(
            Experience(id = "1", title = "Cavafy Museum"),
            Experience(id = "2", title = "Karnak Temple")
        )

        val mockApiResponse = Response.success(
            ExperienceResponse(
                mockResults
            )
        )

        coEvery { getRecentExperiencesUseCase.execute() } returns mockApiResponse

        experienceViewModel.getRecentExperience()

        assert(experienceViewModel.getRecentExperienceStateFlow.value.data?.get(0)?.title == "Cavafy Museum")
    }
}