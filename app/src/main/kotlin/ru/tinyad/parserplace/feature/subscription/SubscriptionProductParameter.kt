package ru.tinyad.parserplace.feature.subscription

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.tinyad.parserplace.core.data.SubscriptionItemParameter
import ru.tinyad.parserplace.R

@Composable
fun SubscriptionProductParameter(
    modifier: Modifier = Modifier,
    index: Int,
    parameter: SubscriptionItemParameter,
    optionsSelected: (key: String, checkedIds: List<Int>) -> Unit
) {

    val options = parameter.options
    var dialogVisible by remember { mutableStateOf(false) }
    val checkedIds = remember { mutableStateListOf<Int>() }

    Column(modifier = modifier) {
        Text(parameter.title)
        Button(onClick = {
            dialogVisible = true
        }) {
            Text("Select regions")
        }
    }

    if (dialogVisible) {
        Dialog(onDismissRequest = {
            dialogVisible = false
        }) {

            Card(
                shape = CardDefaults.outlinedShape,
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(onClick = {
                    dialogVisible = false
                }) {
                    Icon(Icons.Rounded.Close, contentDescription = "")
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 24.dp)
                ) {

                    items(options, key = { it.value }) { opt ->

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

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    OutlinedButton(
                        onClick = {
                            dialogVisible = false
                            optionsSelected(parameter.key, checkedIds)
                        }, modifier = Modifier
                            .padding(top = 12.dp, bottom = 24.dp)
                    ) {
                        Text(stringResource(R.string.com_submit))
                    }
                }
            }
        }
    }
}
