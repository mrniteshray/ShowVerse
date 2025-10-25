package xcom.niteshray.xapps.showverse.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import org.koin.androidx.compose.koinViewModel
import xcom.niteshray.xapps.showverse.data.model.ContentDetail
import xcom.niteshray.xapps.showverse.presentation.components.ShimmerDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    titleId: Int,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(titleId) {
        viewModel.loadContentDetail(titleId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Details",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = {
            if (uiState is DetailUiState.Error) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.loadContentDetail(titleId) }) {
                            Text("Retry")
                        }
                    }
                ) {
                    Text((uiState as DetailUiState.Error).message)
                }
            }
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    ShimmerDetailScreen()
                }
            }
            is DetailUiState.Success -> {
                DetailContent(
                    content = state.content,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is DetailUiState.Error -> {
                ErrorContent(
                    message = state.message,
                    onRetry = { viewModel.loadContentDetail(titleId) },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    content: ContentDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Backdrop Image
        AsyncImage(
            model = content.backdrop ?: content.poster,
            contentDescription = content.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            // Title
            Text(
                text = content.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Release Date and Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                content.releaseDate?.let { date ->
                    InfoChip(label = "Released", value = date)
                }
                
                content.userRating?.let { rating ->
                    InfoChip(label = "Rating", value = "⭐ ${String.format("%.1f", rating)}")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Type and Runtime
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoChip(label = "Type", value = content.type.replace("_", " ").uppercase())
                
                content.runtimeMinutes?.let { runtime ->
                    InfoChip(label = "Runtime", value = "${runtime}min")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Genres
            content.genreNames?.let { genres ->
                if (genres.isNotEmpty()) {
                    Text(
                        text = "Genres",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        mainAxisSpacing = 8.dp,
                        crossAxisSpacing = 8.dp
                    ) {
                        genres.forEach { genre ->
                            AssistChip(
                                onClick = { },
                                label = { Text(genre) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // Plot Overview
            content.plotOverview?.let { plot ->
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = plot,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun InfoChip(label: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}
