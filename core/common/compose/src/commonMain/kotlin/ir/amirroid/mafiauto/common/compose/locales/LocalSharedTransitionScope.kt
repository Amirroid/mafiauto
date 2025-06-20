package ir.amirroid.mafiauto.common.compose.locales

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope =
    compositionLocalOf<SharedTransitionScope> { error("SharedTransitionScope not provided") }