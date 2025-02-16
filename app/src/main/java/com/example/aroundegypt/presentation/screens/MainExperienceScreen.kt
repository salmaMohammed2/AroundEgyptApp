package com.example.aroundegypt.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.aroundegypt.R
import com.example.aroundegypt.domain.enum.ExperienceType
import com.example.aroundegypt.domain.model.Experience
import com.example.aroundegypt.presentation.viewModels.ExperienceViewModel
import com.valentinilk.shimmer.shimmer

@Composable
fun MainExperienceScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel: ExperienceViewModel = hiltViewModel()
    val recommendedExperienceState = viewModel.getRecommendedExperienceStateFlow.collectAsState()
    val recentExperienceState = viewModel.getRecentExperienceStateFlow.collectAsState()
    val searchExperienceState = viewModel.searchExperienceStateFlow.collectAsState()
    var searchWord by remember { mutableStateOf("") }
    var isShowWelcomeText by remember { mutableStateOf(true) }
    var isShowSearchedList by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchBar(query = searchWord, onQueryChange = { newText ->
            searchWord = newText
            isShowSearchedList = false
            isShowWelcomeText = searchWord.isEmpty()

        }, onClearQuery = {
            searchWord = ""
            isShowSearchedList = false
        }, onSearch = {
            viewModel.searchOnExperience(searchWord)
            isShowSearchedList = true
        })

        if (recommendedExperienceState.value.isLoading || recentExperienceState.value.isLoading || searchExperienceState.value.isLoading) {
            LoadingIndicator()
        } else {
            LazyColumn(modifier = Modifier.padding(start = 4.dp)) {
                if (isShowSearchedList) {
                    items(searchExperienceState.value.data ?: emptyList()) { experience ->
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            ExperienceCard(experience = experience, navHostController, viewModel)
                        }
                    }
                } else {
                    item { WelcomeArea() }
                    item {
                        RecommendedExperience(
                            experiences = recommendedExperienceState.value.data ?: emptyList(),
                            navHostController,
                            viewModel
                        )
                    }
                    item {
                        RecentExperience(
                            experiences = recentExperienceState.value.data ?: emptyList(),
                            navHostController,
                            viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onSearch: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable {},
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "Menu Icon"
        )

        Box(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)
                .height(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.gray))
                .padding(start = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch() }),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search Icon",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (query.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.try_luxor),
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                        if (query.isNotEmpty()) {
                            IconButton(onClick = onClearQuery) {
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "Clear Icon",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            )
        }

        Image(
            modifier = Modifier
                .clickable {},
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Filter Icon"
        )
    }
}


@Composable
fun WelcomeArea() {
    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 18.dp)) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = stringResource(id = R.string.welcome),
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(top = 2.dp, start = 2.dp),
            text = stringResource(id = R.string.welcome_text),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RecommendedExperience(
    experiences: List<Experience>,
    navHostController: NavHostController,
    viewModel: ExperienceViewModel
) {
    if (experiences.isNotEmpty()) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.recommended_experience),
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            LazyRow {
                items(experiences) { experience ->
                    ExperienceCard(
                        experience = experience,
                        navHostController,
                        viewModel,
                        ExperienceType.RECOMMENDED.type,
                    )
                }
            }
        }
    }
}

@Composable
fun RecentExperience(
    experiences: List<Experience>,
    navHostController: NavHostController,
    viewModel: ExperienceViewModel
) {
    if (experiences.isNotEmpty()) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.recent_experience),
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            experiences.forEachIndexed { _, experience ->
                ExperienceCard(experience = experience, navHostController, viewModel)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ExperienceCard(
    experience: Experience,
    navHostController: NavHostController,
    viewModel: ExperienceViewModel,
    experienceType: Int = 0
) {
    val isLiked = remember { mutableStateOf(experience.isLiked) }
    val likesNumber = remember { mutableIntStateOf(experience.likesNumber ?: 0) }
    val likedExperienceState by viewModel.likeExperienceStateFlow.collectAsState()

    LaunchedEffect(likedExperienceState) {
        if (likedExperienceState.isSuccess && likedExperienceState.data?.experienceId == experience.id) {
            likesNumber.intValue = likedExperienceState.data?.data ?: likesNumber.intValue
            isLiked.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp - 32.dp)
                .height(154.dp)
                .padding(start = 8.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    navHostController.navigate("experienceDetailsScreen/${experience.id}/${experienceType}")
                }
        ) {
            ImageView(imageUrl = experience.coverPhoto)

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(8.dp)
            ) {
                if (experience.recommended == ExperienceType.RECOMMENDED.type) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(
                                Color.Transparent.copy(alpha = 0.49f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp)
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = "Star Icon",
                                tint = colorResource(id = R.color.orange),
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(4.dp)
                            )

                            Text(
                                text = "RECOMMENDED",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "Info Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)

                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_360),
                    contentDescription = "360 Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                )

                Row(
                    modifier = Modifier.align(Alignment.BottomStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_views),
                        contentDescription = "Views Icon",
                        tint = Color.White,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = experience.viewsNumber.toString(),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_multiple_pictures),
                    contentDescription = "Gallery Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                )
            }

        }

        Row(
            modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp - 32.dp)
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = experience.title.toString(),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = likesNumber.intValue.toString(),
                    color = Color.Black,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            if (experience.isLiked != true) {
                                viewModel.likeAnExperience(experience.id, experienceType)
                                experience.isLiked = true
                                isLiked.value = true
                            }
                        },
                    painter = if (isLiked.value == true) painterResource(id = R.drawable.ic_filled_like) else painterResource(
                        id = R.drawable.ic_empty_like
                    ),
                    contentDescription = "Views Icon",
                    tint = colorResource(id = R.color.orange),
                )
            }
        }
    }
}

@Composable
fun ImageView(imageUrl: String?) {
    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(154.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .shimmer()
                    .background(Color.LightGray)
            )
        }

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .listener(onSuccess = { _, _ -> isLoading = false })
                    .build()
            ),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.orange),
            strokeWidth = 4.dp
        )
    }
}