package com.chumak.testapp.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chumak.testapp.R
import com.chumak.testapp.domain.model.User
import com.chumak.testapp.ui.DETAILS_SCREEN
import com.chumak.testapp.ui.widgets.Avatar
import com.chumak.testapp.ui.widgets.ErrorDialog
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun UserListScreen(viewModel: UserListViewModel, navController: NavController) {
    val state: UserListState by viewModel.stateLiveData.observeAsState(initial = UserListState.empty())
    val isError: Boolean by viewModel.isErrorLiveData.observeAsState(initial = false)

    UserListContent(
        state = state,
        onRefresh = {
            viewModel.initialLoad()
        },
        toLastItemScrolled = {
            viewModel.loadUsers()
        },
        onUserClicked = { login ->
            navController.navigate("$DETAILS_SCREEN/$login")
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
fun UserListContent(
    state: UserListState,
    onRefresh: () -> Unit,
    toLastItemScrolled: () -> Unit,
    onUserClicked: (String) -> Unit
) {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.users)
            TopAppBar(
                title = {
                    Text(text = title)
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 4.dp
            )
        },
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isLoading),
            onRefresh = onRefresh,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                if (state.list.isNotEmpty()) {
                    items(state.list) { user ->

                        if (state.list.last().id == user.id) {
                            toLastItemScrolled()
                        }
                        UserItem(
                            user = user,
                            onUserClicked = onUserClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, onUserClicked: (String) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.clickable {
            onUserClicked(user.login)
        }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Avatar(size = 64.dp, url = user.avatarUrl)
            Spacer(modifier = Modifier.padding(start = 16.dp))
            Text(
                user.login,
            )
        }
    }
}

