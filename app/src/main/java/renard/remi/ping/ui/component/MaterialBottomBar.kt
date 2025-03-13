package renard.remi.ping.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import renard.remi.ping.R
import renard.remi.ping.domain.model.BaseRoute
import renard.remi.ping.extension.removeAllAfterSlash
import renard.remi.ping.ui.home.HomeScreenRoute

data class BottomNavigationItem(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: BaseRoute
)

val bottomBarItems = listOf(
    BottomNavigationItem(
        title = R.string.bottom_bar_home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        route = HomeScreenRoute
    ),
    BottomNavigationItem(
        title = R.string.bottom_bar_cv,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        hasNews = false,
        route = WebViewRoute("https://remirenard.netlify.app/")
    )
)

/**
 * Use this as bottomBar for Scaffold
 */
@Composable
fun MaterialBottomBar(
    isVisible: Boolean,
    currentRoute: String,
    onTabClicked: (BottomNavigationItem) -> Unit,
) {
    if (isVisible) {
        NavigationBar(Modifier.alpha(0.6F)) {
            bottomBarItems.forEachIndexed { _, item ->
                val isSelected = item.route.javaClass.name == currentRoute.removeAllAfterSlash()
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        onTabClicked.invoke(item)
                    },
                    label = {
                        Text(text = stringResource(item.title))
                    },
                    alwaysShowLabel = false,
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                } else if (item.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = stringResource(item.title)
                            )
                        }
                    }
                )
            }
        }
    }
}