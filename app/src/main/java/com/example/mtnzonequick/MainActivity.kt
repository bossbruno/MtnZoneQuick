package com.example.mtnzonequick

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mtnzonequick.ui.theme.MtnZoneQuickTheme
import com.example.mtnzonequick.ui.theme.greenColor


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    val REQUEST_CALL_PERMISSION = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MtnZoneQuickTheme {
                // on below line we are specifying
                // background color for our application
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // on below line we are specifying
                    // theme as scaffold.
                    Scaffold(
                        // in scaffold we are specifying top bar.
                        topBar = {
                            // inside top bar we are specifying
                            // background color.
                            TopAppBar(backgroundColor = greenColor,
                                // along with that we are specifying
                                // title for our top bar.
                                title = {
                                    // in the top bar we are specifying
                                    // title as a text
                                    Text(
                                        // on below line we are specifying
                                        // text to display in top app bar.
                                        text = "MTNQUiCKZONE",

                                        color = Color.White,

                                        // on below line we are specifying
                                        // modifier to fill max width.
                                        modifier = Modifier.fillMaxWidth(),

                                        // on below line we are
                                        // specifying text alignment.
                                        textAlign = TextAlign.Center,

                                        // on below line we are
                                        // specifying color for our text.
                                    )
                                }
                            )
                        }
                    ) {
                        // on below line we are calling open
                        // dialer method to open dialer.
                        openDialer()
                    }
                }
            }


            val permission = Manifest.permission.CALL_PHONE

            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    REQUEST_CALL_PERMISSION
                )


            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CALL_PERMISSION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.equals(1)
                    && grantResults[0] === PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the phone call
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

    @Composable
    fun openDialer() {

        // in the below line, we are
        // creating variables for URL
        val phoneNumber = remember {
            mutableStateOf(TextFieldValue())
        }

        // on below line we are creating
        // a variable for a context
        val ctx = LocalContext.current

        // on below line we are creating a column
        Column(
            // on below line we are specifying modifier
            // and setting max height and max width
            // for our column
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth()
                // on below line we are
                // adding padding for our column
                .padding(5.dp),
            // on below line we are specifying horizontal
            // and vertical alignment for our column
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // in the below line, we are creating a
            // text field for getting URL from user.
            TextField(
                // on below line we are specifying value
                // for our text field.
                value = "*135*2*1#",

                // on below line we are adding on value
                // change for text field.
                onValueChange = { phoneNumber },

                // on below line we are adding place holder as text
                placeholder = { Text(text = "*135*2*1" + "#") },

                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                // on below line we are adding single line to it.
                singleLine = true,
            )

            // on below line adding a spacer.
            Spacer(modifier = Modifier.height(20.dp))

            // on below line adding a button to open URL
            Button(onClick = {

                // on below line we are opening the dialer of our
                // phone and passing phone number.
                // Use format with "tel:" and phoneNumber created is
                // stored in u.
                val u = "tel:" + "*135*2*1%23"

                // Create the intent and set the data for the
                // intent as the phone number.
                val p = Intent(Intent.ACTION_CALL).apply { data = Uri.parse(u) }
                // val i = Intent(Intent.ACTION_DIAL, u.toUri())
                try {

                    // Launch the Phone app's dialer with a phone
                    // number to dial a call.
                    //  ctx.startActivity(i)


                    ctx.startActivity(p)
                } catch (s: SecurityException) {
                    Log.e("Dial error", "$s")
                    // show() method display the toast with
                    // exception message.
                    Toast.makeText(ctx, "$s", Toast.LENGTH_LONG)
                        .show()
                }


            }) {
                // on below line creating a text for our button.
                Text(
                    // on below line adding a text ,
                    // padding, color and font size.
                    text = "Dial",
                    modifier = Modifier.padding(10.dp),
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}
