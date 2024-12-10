package ru.tinyad.parserplace.feature.subscription

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import ru.tinyad.parserplace.core.model.data.NumberOption

@Composable
fun SubscriptionsScreen(modifier: Modifier = Modifier) {

    SubscriptionEditScreen()
//    SampleDialog()
}

@Composable
@Preview
fun SampleDialog() {

    val options = remember {
        listOf<NumberOption>(
            NumberOption(1, "Primer"),
            NumberOption(2, "Another"),
            NumberOption(3, "Sample"),
            NumberOption(4, "Data"),
            NumberOption(5, "Exists"),
        )
    }

    val checkedIds = remember { mutableStateListOf<Int>() }

    Dialog(onDismissRequest = {}) {

        Card(
            shape = CardDefaults.outlinedShape,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {

                options.forEach { opt ->

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Checkbox(
                            checkedIds.contains(opt.value),
                            onCheckedChange = { checked ->
                                if (checked) checkedIds.add(opt.value)
                                else checkedIds.remove(opt.value)
                            }
                        )

                        Text(opt.text)
                    }
                }
            }
        }
    }
}
