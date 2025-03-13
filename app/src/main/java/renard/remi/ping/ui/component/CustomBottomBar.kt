package renard.remi.ping.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import renard.remi.ping.R
import renard.remi.ping.extension.removeAllAfterSlash
import renard.remi.ping.ui.theme.PingTheme
import kotlin.math.PI
import kotlin.math.sin

/**
 * Don't use this as a bottomBar and don't put it inside the Nav host
 * Use this as a component of the main scaffold
 * This is due to animation which increase too much the size of classics bottom bar
 */
@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    currentRoute: String,
    onTabClicked: (BottomNavigationItem) -> Unit,
    onSettingsClicked: () -> Unit,
) {
    val isMenuExtended = remember { mutableStateOf(false) }

    val fabAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 700,
            easing = LinearEasing
        )
    )

    val clickAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing
        )
    )

    AnimatedVisibility(
        isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomBarContent(
                currentRoute = currentRoute,
                items = bottomBarItems,
                onSettingsClicked = onSettingsClicked,
                onTabClicked = onTabClicked,
                fabAnimationProgress = fabAnimationProgress,
                clickAnimationProgress = clickAnimationProgress
            ) {
                isMenuExtended.value = isMenuExtended.value.not()
            }
        }
    }
}

@Composable
fun BottomBarContent(
    currentRoute: String,
    items: List<BottomNavigationItem>,
    onSettingsClicked: () -> Unit,
    onTabClicked: (BottomNavigationItem) -> Unit,
    fabAnimationProgress: Float = 0f,
    clickAnimationProgress: Float = 0f,
    toggleAnimation: () -> Unit = { }
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        CustomBottomNavigation(currentRoute, items, onTabClicked)
        Circle(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            animationProgress = 0.5f
        )
        FabGroup(
            onSettingsClicked = onSettingsClicked,
            animationProgress = fabAnimationProgress,
            toggleAnimation = toggleAnimation
        )
        Circle(
            color = Color.White,
            animationProgress = clickAnimationProgress
        )
    }
}

@Composable
fun Circle(color: Color, animationProgress: Float) {
    val animationValue = sin(PI * animationProgress).toFloat()
    Box(
        modifier = Modifier
            .padding(44.dp)
            .size(56.dp)
            .scale(2 - animationValue)
            .border(
                width = 2.dp,
                color = color.copy(alpha = color.alpha * animationValue),
                shape = CircleShape
            )
    )
}

@Composable
fun CustomBottomNavigation(
    currentRoute: String,
    items: List<BottomNavigationItem>,
    onTabClicked: (BottomNavigationItem) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(80.dp)
            .alpha(0.8F)
            .paint(
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primaryContainer),
                painter = painterResource(R.drawable.bottom_navigation),
                contentScale = ContentScale.FillHeight
            )
            .padding(horizontal = 40.dp)
    ) {
        items.map { item ->
            val isSelected = item.route.javaClass.name == currentRoute.removeAllAfterSlash()
            IconButton(onClick = { onTabClicked.invoke(item) }) {
                Icon(
                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun FabGroup(
    animationProgress: Float = 0f,
    onSettingsClicked: () -> Unit,
    toggleAnimation: () -> Unit = { }
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 44.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        AnimatedFab(
            icon = Icons.Default.Call,
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 72.dp,
                        end = 210.dp
                    ) * FastOutSlowInEasing.transform(0f, 0.8f, animationProgress)
                ),
            opacity = LinearEasing.transform(0.2f, 0.7f, animationProgress)
        )

        AnimatedFab(
            icon = Icons.Default.Settings,
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 88.dp,
                    ) * FastOutSlowInEasing.transform(0.1f, 0.9f, animationProgress)
                ),
            onClick = onSettingsClicked,
            opacity = LinearEasing.transform(0.3f, 0.8f, animationProgress)
        )

        AnimatedFab(
            icon = Icons.Default.ShoppingCart,
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 72.dp,
                    start = 210.dp
                ) * FastOutSlowInEasing.transform(0.2f, 1.0f, animationProgress)
            ),
            opacity = LinearEasing.transform(0.4f, 0.9f, animationProgress)
        )

        AnimatedFab(
            modifier = Modifier
                .scale(1f - LinearEasing.transform(0.5f, 0.85f, animationProgress)),
        )

        AnimatedFab(
            icon = Icons.Default.Add,
            modifier = Modifier
                .rotate(
                    225 * FastOutSlowInEasing
                        .transform(0.35f, 0.65f, animationProgress)
                ),
            onClick = toggleAnimation,
            backgroundColor = Color.Transparent
        )
    }
}

@Composable
fun AnimatedFab(
    modifier: Modifier,
    icon: ImageVector? = null,
    opacity: Float = 1f,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = backgroundColor,
        modifier = modifier
            .scale(1.25f)
            .clip(CircleShape)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White.copy(alpha = opacity)
            )
        }
    }
}

fun Easing.transform(from: Float, to: Float, value: Float): Float {
    return transform(((value - from) * (1f / (to - from))).coerceIn(0f, 1f))
}

operator fun PaddingValues.times(value: Float): PaddingValues = PaddingValues(
    top = calculateTopPadding() * value,
    bottom = calculateBottomPadding() * value,
    start = calculateStartPadding(LayoutDirection.Ltr) * value,
    end = calculateEndPadding(LayoutDirection.Ltr) * value
)

@Composable
@Preview(device = "id:pixel_9")
private fun MainScreenPreview() {
    PingTheme {
        CustomBottomBar(
            isVisible = true,
            currentRoute = "",
            onTabClicked = {},
            onSettingsClicked = {}
        )
    }
}
