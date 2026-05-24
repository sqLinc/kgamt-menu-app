package com.kgamt.menu.app.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kgamt.menu.app.domain.models.FoodResponseDto

@Composable
fun RequestItemCard(request: FoodResponseDto) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            // 🔹 Дата
            Text(
                text = request.date.toString(),
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(Modifier.height(6.dp))

            // 🔹 Количество
            Text(
                text = "С супом: ${request.withSoup}, без супа: ${request.withoutSoup}",
                style = MaterialTheme.typography.bodyMedium
            )

            // 🔹 Стоимость
            Text(
                text = "Сумма: ${request.totalCost}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(6.dp))

            // 🔹 Статусы
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusChip(
                    text = if (request.isPaid == true) "Оплачено" else "Не оплачено",
                    isActive = request.isPaid == true
                )

                StatusChip(
                    text = if (request.isConfirmed == true) "Подтверждено" else "Не подтверждено",
                    isActive = request.isConfirmed == true
                )
            }
        }
    }
}