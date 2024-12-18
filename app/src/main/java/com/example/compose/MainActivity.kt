package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Surface () {
                    TipTimeLayout()
                }


            }
        }
    }
}

@Composable
fun EditInputField(@StringRes label:Int, @DrawableRes iconId:Int, modifier: Modifier = Modifier, value:String,  onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(iconId), null)},
        label = { Text(stringResource(label))},
        modifier = modifier,
        keyboardOptions =  KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
}
@Composable
fun RoundTheTipRow(modifier: Modifier = Modifier, rounUp:Boolean, onRoundUpChanged: (Boolean) -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth().size(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            text= stringResource(R.string.round_up_tip)
        )
        Switch(
            checked = rounUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier.fillMaxWidth().wrapContentWidth(Alignment.End).
            padding(vertical = 21.dp)
        )
    }
}


@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    var discountInput by remember { mutableStateOf("15.0") }
    var roundUpInput by remember { mutableStateOf(false) }
    val roundUp = roundUpInput
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val discount = discountInput.toDoubleOrNull() ?: 15.0
    val tip  = calculateTip(amount, discount, roundUp)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditInputField(
            label = R.string.bill_amount,
            iconId = R.drawable.money,
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
            amountInput,
            {amountInput = it}
        )
        EditInputField(
            label = R.string.discount_amount,
            iconId = R.drawable.percent,
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
            discountInput,
            {discountInput = it}
        )
        RoundTheTipRow(
            modifier = Modifier.padding(bottom = 32.dp),
            roundUpInput, {roundUpInput = it}
        )
        Spacer(modifier = Modifier.height(90.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            color = Color.DarkGray,
            fontWeight = FontWeight(800)
        )

    }
}

@Composable
@Preview
fun TipTimeLayoutPreview() {
    TipTimeLayout()
}

@VisibleForTesting
internal fun  calculateTip(amount:Double, discount:Double, round:Boolean): Double {
    var tip = discount / 100 * amount
    if (round) {
        tip = kotlin.math.ceil(tip)
    }
    return tip
}