package dev.tarjanyicsanad.fluentio.android.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.Scene
import androidx.navigation3.ui.SceneStrategy
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

class TwoPaneScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val firstEntry: NavEntry<T>,
    val secondEntry: NavEntry<T>,
) : Scene<T> {
    override val entries: List<NavEntry<T>>
        get() = listOf(firstEntry, secondEntry)

    override val content: @Composable (() -> Unit)
        get() = {
            Row(modifier = Modifier.fillMaxHeight()) {
                Box(modifier = Modifier.weight(0.3f)) {
                    firstEntry.Content()
                }
                Box(modifier = Modifier.weight(0.7f)) {
                    secondEntry.Content()
                }
            }
        }

    companion object {
        const val TWO_PANE_KEY = "two_pane"

        fun twoPane() = mapOf(TWO_PANE_KEY to true)
    }
}

class TwoPaneSceneStrategy<T : Any> : SceneStrategy<T> {
    @Composable
    override fun calculateScene(
        entries: List<NavEntry<T>>,
        onBack: (Int) -> Unit
    ): Scene<T>? {
        val windowClass = currentWindowAdaptiveInfo().windowSizeClass

        if (!windowClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
            return null;
        }

        val lastTwoEntries = entries.takeLast(2)
        val hasTwoPaneKey = lastTwoEntries.all {
            it.metadata.containsKey(TwoPaneScene.TWO_PANE_KEY) && it.metadata[TwoPaneScene.TWO_PANE_KEY] == true
        }

        return if (lastTwoEntries.size == 2 && hasTwoPaneKey) {
            val firstEntry = lastTwoEntries.first()
            val lastEntry = lastTwoEntries.last()
            TwoPaneScene(
                key = firstEntry.contentKey to lastEntry.contentKey,
                previousEntries = entries.dropLast(1),
                firstEntry = firstEntry,
                secondEntry = lastEntry,
            )
        } else {
            null
        }
    }
}