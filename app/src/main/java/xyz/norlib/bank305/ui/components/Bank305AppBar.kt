package xyz.norlib.bank305.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import xyz.norlib.bank305.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bank305AppBar(){
    CenterAlignedTopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.logotype__fr),
            contentDescription = null
        )
    })
}