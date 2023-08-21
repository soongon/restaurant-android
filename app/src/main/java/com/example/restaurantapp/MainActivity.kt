package com.example.restaurantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RestaurantScreen()
                }
            }
        }
    }
}

@Composable
fun RestaurantScreen() {

    val viewModel: RestaurantsViewModel = viewModel()
    viewModel.getRestaurants()

    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        )
    ) {
        items(viewModel.state.value) {restaurant ->
            RestaurantItem(item = restaurant) {id ->
                viewModel.toggleFavorite(id)
            }
        }
    }
}


@Composable
private fun RestaurantItem(item: Restaurant,
                           onClick: (id: Int) -> Unit) {

    val icon = if (item.isFavorite) Icons.Filled.Favorite
                else Icons.Filled.FavoriteBorder

    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(
                icon = Icons.Filled.Place,
                modifier = Modifier.weight(0.15f))
            RestaurantDetails(
                title = item.title,
                description = item.description,
                modifier = Modifier.weight(0.70f),
            )
            RestaurantIcon(
                modifier = Modifier.weight(0.15f),
                icon = icon
            ) {
                onClick(item.id)
            }
        }
    }
}

@Composable
private fun FavoriteIcon(modifier: Modifier = Modifier,
                        icon: ImageVector,
                        onClick: () -> Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = "",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() },
    )
}

@Composable
private fun RestaurantDetails(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun RestaurantIcon(icon: ImageVector,
                           modifier: Modifier = Modifier,
                           onClick: () -> Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    RestaurantScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NameInput() {
    var textState by remember { mutableStateOf("") }
    TextField(
        value = "",
        onValueChange = { newValue ->
            textState = newValue
        },
        label = {
            Text(text = "Your name")
        }
    )
}
