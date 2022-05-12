package com.ironic.composefeed

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ironic.composefeed.model.News
import com.ironic.composefeed.ui.theme.ComposeFeedTheme
import com.ironic.composefeed.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()

        setContent {
            ComposeFeedTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text(text = "Yet Another News App") }) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    HomeScreen(viewModel, ::onNewsClicked)
                }
            }
        }

        viewModel.fetchNews()
    }

    private fun onNewsClicked(news: News) {
        startActivity(
            Intent(this, WebViewActivity::class.java).putExtra("url", news.url)
        )
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel, onNewsClicked: (News) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 12.dp, end = 12.dp)
    ) {
        NewsFeed(newsList = viewModel.postList.value, onNewsClicked = onNewsClicked)
    }
}

@Composable
fun NewsFeed(newsList: List<News>, onNewsClicked: (News) -> Unit) {
    LazyColumn {
        item { Spacer(Modifier.size(12.dp)) }
        items(newsList) {
            NewsItem(it, onNewsClicked)
            Spacer(Modifier.size(12.dp))
        }
    }
}

@Composable
fun NewsItem(news: News, onNewsClicked: (News) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color(0xFFE2E2E2))
            .padding(bottom = 4.dp)
            .clickable { onNewsClicked(news) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(news.urlToImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            placeholder = painterResource(R.drawable.news_placeholder),
            error = painterResource(id = R.drawable.news_placeholder),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = news.title.toString(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview
@Composable
fun PreviewNewsItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        NewsFeed(
            newsList = listOf(
                News(
                    title = "Russia Ukraine War News Live Updates: Finland to apply for Nato membership; Kharkiv pushes back Russian forces - The Indian Express",
                    url = "https://indianexpress.com/article/world/russia-ukraine-war-live-updates-putin-zelenskyy-7909119/",
                    urlToImage = "https://images.indianexpress.com/2022/05/ukraine-playground.jpg"
                ),
                News(
                    title = "Russia Ukraine War News Live Updates: Finland to apply for Nato membership; Kharkiv pushes back Russian forces - The Indian Express",
                    url = "https://indianexpress.com/article/world/russia-ukraine-war-live-updates-putin-zelenskyy-7909119/",
                    urlToImage = "https://images.indianexpress.com/2022/05/ukraine-playground.jpg"
                ),
                News(
                    title = "Russia Ukraine War News Live Updates: Finland to apply for Nato membership; Kharkiv pushes back Russian forces - The Indian Express",
                    url = "https://indianexpress.com/article/world/russia-ukraine-war-live-updates-putin-zelenskyy-7909119/",
                    urlToImage = "https://images.indianexpress.com/2022/05/ukraine-playground.jpg"
                ),
                News(
                    title = "Russia Ukraine War News Live Updates: Finland to apply for Nato membership; Kharkiv pushes back Russian forces - The Indian Express",
                    url = "https://indianexpress.com/article/world/russia-ukraine-war-live-updates-putin-zelenskyy-7909119/",
                    urlToImage = "https://images.indianexpress.com/2022/05/ukraine-playground.jpg"
                )
            ), onNewsClicked = {})
    }
}