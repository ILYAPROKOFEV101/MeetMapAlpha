package com.yourmood.yourmood.ui.UI

import androidx.compose.ui.graphics.vector.ImageVector

class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
    ) {
    }
