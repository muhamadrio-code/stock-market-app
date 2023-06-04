package com.riopermana.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riopermana.data.model.CompanyListings

@Composable
fun CompanyItem(
    modifier: Modifier = Modifier,
    companyListings: CompanyListings
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = companyListings.name,
                modifier = Modifier.weight(4f),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(Modifier.width(4.dp))
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = companyListings.exchange,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    textAlign = TextAlign.End,
                    fontStyle = FontStyle.Italic,
                )
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "(${companyListings.symbol})",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyItemPreview() {
    CompanyItem(
        companyListings = CompanyListings(
            symbol = "AA", name = "company name", exchange = "NYSE"
        )
    )
}

