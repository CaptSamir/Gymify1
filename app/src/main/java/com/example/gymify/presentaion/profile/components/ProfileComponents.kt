package com.example.gymify.presentaion.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileToggleItem(label: String, checked: Boolean, onCheckedChange:(Boolean) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun ProfileItem(label: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp)
    }
}
