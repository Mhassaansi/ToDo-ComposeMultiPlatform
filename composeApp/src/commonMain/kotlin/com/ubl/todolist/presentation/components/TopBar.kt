package com.ubl.todolist.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCommon(
    title: String,
    showBack: Boolean = false,
    showEdit: Boolean = false,
    showSearch: Boolean = false,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            if (showSearch) {
                IconButton(onClick = { onSearchClick() }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
            if (showEdit) {
                IconButton(onClick = { onEditClick() }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTasks(
    title: String,
    onSearchClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(0.7f), // occupy left portion
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            // Box containing two icons
            Box {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                }
            }
        }
    )
}

