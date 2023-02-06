package com.pam.musicplayerapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pam.musicplayerapp.ui.theme.MusicplayerappTheme

class HomeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicplayerappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val username = getIntent().getStringExtra("username") ?: ""
                    MusicApp(username)
                    ScaffoldCompose()
                }
            }
        }
    }
}

@Composable
fun MusicApp(name: String) {
    Column(
        modifier = Modifier
            .background(Color(3, 15, 21))
            .fillMaxWidth()
    )
    {
        Text(
            text = "Daily Mix",
            fontSize = 26.sp,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 160.dp)
        ) {

            Spacer(modifier = Modifier.padding(top = 35.dp, bottom = 30.dp))
            Spacer(modifier = Modifier.height(115.dp))
            Image(painter = painterResource(id = R.drawable.singer),
                contentDescription = "Singer",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .sizeIn(maxHeight = 500.dp, maxWidth = 500.dp)
                    .width(350.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .aspectRatio(1f)
                    .weight(9f)
            )
            Spacer(modifier = Modifier.padding(top = 155.dp, bottom = 20.dp))
            SongDescription("Singer Name", "Title Songs")
            PlayerSlider()
            SideButton()
            Spacer(modifier = Modifier.padding(top = 2.5.dp, bottom = 1.dp))
            Column() {
                Spacer(modifier = Modifier.padding(top = 10.dp))
                PlayerButton(modifier = Modifier.padding(start = 24.dp, top = 100.dp,end = 24.dp))
            }
            Spacer(modifier = Modifier.weight(2.2f))
        }
    }
}


@Composable
fun ScaffoldCompose()
{
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = {MusicApp(name = String())},
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val destinations = listOf(Screen.DestinationA, Screen.DestinationB, Screen.DestinationC, Screen.DestinationD)
    BottomNavigation(backgroundColor = Color(3, 15, 21)){

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        destinations.forEach{
                item -> BottomNavigationItem(
            icon = { Icon(item.icon, contentDescription = null)},
            label = {Text(text = item.label)},
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White,
            alwaysShowLabel = false,
            selected = currentRoute == item.route,
            onClick = {
                navController.navigate(item.route)
                {
                    navController.graph.startDestinationRoute?.let { route -> popUpTo(route)
                    {
                        saveState = true
                    }}
                    launchSingleTop = true
                    restoreState = true
                }
            }

        )

        }
    }
}

sealed class Screen(val route:String, val label:String, val icon: ImageVector)
{
    object DestinationA: Screen("Play", "Play", Icons.Default.PlayArrow)
    object DestinationB: Screen("Favourite", "favorite", Icons.Default.FavoriteBorder)
    object DestinationC: Screen("Search", "Search", Icons.Default.Search)
    object DestinationD: Screen("Profile", "Account", Icons.Default.AccountCircle)
}

@Composable
fun PlayerButton(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 70.dp,
    sideButtonSize: Dp = 38.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_shuffle_24),
            contentDescription = "Shuffle",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.skip_previous),
            contentDescription = "Previous",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_stop_24),
            contentDescription = "stop",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.skip_next),
            contentDescription = "Next",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_multiple_stop_24),
            contentDescription = "Skip Next",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier
        )
    }
}

@Composable
fun SideButton(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 27.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_favorite_border_24),
            contentDescription = "favourite",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
        )
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, end = 1.dp),horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_volume_up_24),
                contentDescription = "Sound",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier,
            )
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_download_24),
            contentDescription = "download",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
            )
        }

    }

}

@Composable
fun PlayerSlider() {
    Column(
        modifier = Modifier
            .padding(24.dp, top = 12.dp, end = 24.dp)
    ) {
        Slider(
            value = 0.33f,
            onValueChange = {},
            colors = SliderDefaults.colors(
                thumbColor = Color(29,186,195),
                activeTrackColor = Color(29,186,195)
            )
        )
    }
}

@Composable
fun SongDescription(title: String, name: String) {
    Text(
        text = "Ed Sheeran",
        style = MaterialTheme.typography.h5,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = "Photograph",
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            color = Color.White
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true
)
@Composable
fun DefaultPreview() {
    MusicplayerappTheme {
        MusicApp("Android")
        ScaffoldCompose()
    }
}