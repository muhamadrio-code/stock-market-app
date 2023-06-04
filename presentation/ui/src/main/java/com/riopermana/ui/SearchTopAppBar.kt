package com.riopermana.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    query: String? = null,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onSearchImeAction: (KeyboardActionScope.() -> Unit)? = null,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: SimpleTopAppBarColors = SimpleTopAppBarColors.appBarColors(),
) {
    SearchTopAppBarLayout(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onTrailingIconClick = onTrailingIconClick,
        onSearchImeAction = onSearchImeAction,
        scrollBehavior = scrollBehavior,
        windowInsets = windowInsets,
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBarLayout(
    modifier: Modifier = Modifier,
    query: String? = null,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onSearchImeAction: (KeyboardActionScope.() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior?,
    windowInsets: WindowInsets,
    colors: SimpleTopAppBarColors,
) {
    // Sets the app bar's height offset to collapse the entire bar's height when content is
    // scrolled.
    val containerHeight = 72.dp
    val heightOffsetLimit =
        with(LocalDensity.current) { -containerHeight.toPx() }
    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != heightOffsetLimit) {
            scrollBehavior?.state?.heightOffsetLimit = heightOffsetLimit
        }
    }

    // Obtain the container color from the TopAppBarColors using the `overlapFraction`. This
    // ensures that the colors will adjust whether the app bar behavior is pinned or scrolled.
    // This may potentially animate or interpolate a transition between the container-color and the
    // container's scrolled-color according to the app bar's scroll state.
    val overlappedFraction = scrollBehavior?.state?.overlappedFraction ?: 0f
    val fraction = if (overlappedFraction > 0.01f) 1f else 0f
    val appBarContainerColor by animateColorAsState(
        targetValue = colors.containerColor(fraction),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    // Set up support for resizing the top app bar when vertically dragging the bar itself.
    val appBarDragModifier = if (scrollBehavior != null && !scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec
                )
            }
        )
    } else {
        Modifier
    }

    // Compose a Surface with a TopAppBarLayout content.
    // The surface's background color is animated as specified above.
    // The height of the app bar is determined by subtracting the bar's height offset from the
    // app bar's defined constant height value (i.e. the ContainerHeight token).
    Surface(modifier = modifier.then(appBarDragModifier), color = appBarContainerColor) {
        val scrollOffset = scrollBehavior?.state?.heightOffset ?: 0f
        val height = LocalDensity.current.run {
            containerHeight.toPx() + scrollOffset
        }
        val alpha = 1f - (scrollBehavior?.state?.collapsedFraction ?: 0f)

        Layout(
            {
                SearchBar(
                    query = query,
                    onQueryChange = onQueryChange,
                    onTrailingIconClick = onTrailingIconClick,
                    onSearchImeAction = onSearchImeAction,
                    modifier = Modifier
                        .padding(16.dp)
                        .layoutId("searchAction")
                )
            }, modifier = Modifier
                .windowInsetsPadding(windowInsets)
                // clip after padding so we don't show the title over the inset area
                .clipToBounds()
        ) { measurable, constraints ->
            val layoutHeight = height.roundToInt()

            val searchPlaceable = measurable.first {
                it.layoutId == "searchAction"
            }.measure(constraints.copy(minWidth = 0))

            layout(constraints.maxWidth, layoutHeight) {
                searchPlaceable.placeRelativeWithLayer(
                    x = 0,
                    y = scrollOffset.roundToInt() +
                            (containerHeight.toPx().roundToInt() - searchPlaceable.height) / 2
                ) {
                    this.alpha = alpha
                }
            }
        }
    }
}

class SimpleTopAppBarColors internal constructor(
    private val containerColor: Color,
    private val scrolledContainerColor: Color,
) {
    @Composable
    internal fun containerColor(colorTransitionFraction: Float): Color {
        return lerp(
            containerColor,
            scrolledContainerColor,
            FastOutLinearInEasing.transform(colorTransitionFraction)
        )
    }

    companion object {
        @Composable
        fun appBarColors(
            containerColor: Color = MaterialTheme.colorScheme.surface,
            scrolledContainerColor: Color = MaterialTheme.colorScheme.surface,
        ) = SimpleTopAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {
    // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the app bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}