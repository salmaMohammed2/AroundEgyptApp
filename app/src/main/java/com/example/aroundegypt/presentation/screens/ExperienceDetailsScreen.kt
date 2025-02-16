package com.example.aroundegypt.presentation.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.aroundegypt.R
import com.example.aroundegypt.presentation.viewModels.ExperienceDetailsViewModel

@Composable
fun ExperienceDetailsScreen(experienceId: String, experienceType: Int) {
    val viewModel: ExperienceDetailsViewModel = hiltViewModel()
    val experience = viewModel.getExperienceById(experienceId, experienceType)
    val isLiked = remember { mutableStateOf(experience?.isLiked) }
    val likesNumber = remember { mutableIntStateOf(experience?.likesNumber ?: 0) }
    val likedExperienceState by viewModel.likeExperienceStateFlow.collectAsState()

    LaunchedEffect(likedExperienceState) {
        if (likedExperienceState.isSuccess) {
            likedExperienceState.data?.data?.let { newLikesCount ->
                likesNumber.intValue = newLikesCount
            }
        }
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = experience?.coverPhoto,
                        builder = {
                            crossfade(true)
                            transformations(RoundedCornersTransformation(10f))
                        }
                    ),
                    contentDescription = "Experience Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(152.dp)
                        .height(46.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.explore_now),
                        color = colorResource(id = R.color.orange),
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_views),
                        contentDescription = "Views Icon",
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = experience?.viewsNumber.toString(),
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
                        .padding(8.dp)
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row {
                    experience?.title?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "Share Icon",
                            tint = colorResource(id = R.color.orange)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    if (experience?.isLiked != true) {
                                        experience?.id?.let {
                                            viewModel.likeAnExperience(
                                                it,
                                                experienceType
                                            )
                                        }
                                        experience?.isLiked = true
                                        isLiked.value = true
                                    }
                                },
                            painter = if (isLiked.value == true) painterResource(id = R.drawable.ic_filled_like) else painterResource(
                                id = R.drawable.ic_empty_like
                            ),
                            contentDescription = "Like Icon",
                            tint = colorResource(id = R.color.orange)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        experience?.likesNumber?.let {
                            Text(
                                text = likesNumber.intValue.toString(),
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                experience?.address?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.address)
                    )
                }
            }
        }

        item {
            Divider(
                color = colorResource(id = R.color.divider),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                experience?.description?.let {
                    Text(
                        text = stringResource(id = R.string.description),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}