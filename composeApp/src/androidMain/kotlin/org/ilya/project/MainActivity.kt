package org.ilya.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
         //  val intent = Intent(this@MainActivity, AuthActivity::class.java)
           //  startActivity(intent)
        }
    }
}

//
@Preview
@Composable
fun AppAndroidPreview() {
    App()
    Text(text = stringResource(R.string.app_name))
}