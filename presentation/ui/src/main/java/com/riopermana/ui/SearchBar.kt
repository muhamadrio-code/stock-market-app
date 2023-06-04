package com.riopermana.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String? = null,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onSearchImeAction: (KeyboardActionScope.() -> Unit)? = null,
) {
    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = query ?: "",
            onValueChange = onQueryChange,
            placeholder = { Text(text = "Search...") },
            singleLine = true,
            shape = RoundedCornerShape(50),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (!query.isNullOrEmpty()) {
                    IconButton(
                        onClick = onTrailingIconClick
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear Text")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = onSearchImeAction
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary
            ),
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    var text by remember { mutableStateOf("") }

    SearchBar(
        Modifier.fillMaxWidth(),
        text,
        { text = it },
        {}
    )
}