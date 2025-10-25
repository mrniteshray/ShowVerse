package xcom.niteshray.xapps.showverse.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import xcom.niteshray.xapps.showverse.core.util.Constants
import xcom.niteshray.xapps.showverse.data.model.ContentItem
import xcom.niteshray.xapps.showverse.presentation.components.ContentItemCard
import xcom.niteshray.xapps.showverse.presentation.components.ShimmerContentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onContentClick: (Int) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Show snackbar when there's an error
    LaunchedEffect(uiState) {
        if (uiState is HomeUiState.Error) {
            val result = snackbarHostState.showSnackbar(
                message = (uiState as HomeUiState.Error).message,
                actionLabel = "Retry",
                duration = SnackbarDuration.Indefinite
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.loadContent()
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🎬 ShowVerse",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    LoadingContent()
                }

                is HomeUiState.Success -> {
                    SuccessContent(
                        state = state,
                        onTabSelected = viewModel::selectTab,
                        onContentClick = onContentClick
                    )
                }

                is HomeUiState.Error -> {
                    // Show empty state when error occurs
                    // Snackbar will handle the error message with retry action
                    EmptyStateContent()
                }
            }
        }
    }
}


@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab placeholder with better styling
        TabRow(
            selectedTabIndex = 0,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = true, 
                onClick = {}, 
                text = { 
                    Text(
                        "🎥 Movies",
                        fontWeight = FontWeight.Bold
                    ) 
                }
            )
            Tab(
                selected = false, 
                onClick = {}, 
                text = { 
                    Text(
                        "📺 TV Shows",
                        fontWeight = FontWeight.Normal
                    ) 
                }
            )
        }
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            items(Constants.SHIMMER_ITEM_COUNT) {
                ShimmerContentItem()
            }
        }
    }
}

@Composable
private fun SuccessContent(
    state: HomeUiState.Success,
    onTabSelected: (ContentTab) -> Unit,
    onContentClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Stylish Tabs with better colors
        TabRow(
            selectedTabIndex = if (state.selectedTab == ContentTab.MOVIES) 0 else 1,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[if (state.selectedTab == ContentTab.MOVIES) 0 else 1]),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            Tab(
                selected = state.selectedTab == ContentTab.MOVIES,
                onClick = { onTabSelected(ContentTab.MOVIES) },
                text = { 
                    Text(
                        "🎥 Movies",
                        fontWeight = if (state.selectedTab == ContentTab.MOVIES) FontWeight.Bold else FontWeight.Normal
                    ) 
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Tab(
                selected = state.selectedTab == ContentTab.TV_SHOWS,
                onClick = { onTabSelected(ContentTab.TV_SHOWS) },
                text = { 
                    Text(
                        "📺 TV Shows",
                        fontWeight = if (state.selectedTab == ContentTab.TV_SHOWS) FontWeight.Bold else FontWeight.Normal
                    ) 
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // Content Grid
        val items = if (state.selectedTab == ContentTab.MOVIES) state.movies else state.tvShows
        
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📭",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No content available",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                items(items) { content ->
                    ContentItemCard(
                        content = content,
                        onClick = { onContentClick(content.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyStateContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "⚠️",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Unable to load content",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Check the error message below",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
