package com.chumak.testapp.ui.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chumak.testapp.ui.widgets.Avatar
import com.chumak.testapp.R
import com.chumak.testapp.ui.widgets.ErrorDialog

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel, navController: NavController) {
    val state: UserDetailsState by viewModel.stateLiveData.observeAsState(initial = UserDetailsState.empty())
    val isError: Boolean by viewModel.isErrorLiveData.observeAsState(initial = false)

    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW) }

    UserDetailsContent(
        state = state,
        navController = navController,
        onBlogClicked = { blog ->
            intent.data = Uri.parse(blog)
            context.startActivity(intent)
        }
    )

    if (isError) {
        ErrorDialog(
            onDismiss = {
                viewModel.onErrorDismiss()
            }
        )
    }
}

@Composable
fun UserDetailsContent(
    state: UserDetailsState,
    navController: NavController,
    onBlogClicked: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.details.login)
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 4.dp,
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    null
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(32.dp))
            Avatar(size = 120.dp, url = state.details.avatarUrl)
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = state.details.login,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = createLink(state.details.blog),
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    onBlogClicked(state.details.blog)
                }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = stringResource(R.string.repos, state.details.publicRepos),
                    fontSize = 14.sp,
                )
                Text(
                    text = stringResource(R.string.gists, state.details.publicGists),
                    fontSize = 14.sp,
                )
                Text(
                    text = stringResource(R.string.followers, state.details.followers),
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun createLink(blog: String): AnnotatedString {
    return buildAnnotatedString {
        append(blog)
        colorResource(R.color.grey)
        addStyle(
            style = SpanStyle(
                color = colorResource(R.color.grey),
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            ), start = 0, end = blog.lastIndex + 1
        )
    }
}