package com.example.appparaestudo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appparaestudo.ui.theme.Bege
import com.example.appparaestudo.ui.theme.TextoClaro
import com.example.appparaestudo.ui.theme.VerdeClaro
import com.example.appparaestudo.ui.theme.VerdeEscuro

@Composable
fun TelaProgresso(modifier: Modifier = Modifier) {
    var aba by remember { mutableStateOf("Semana") }
    val abas = listOf("Semana", "Mês", "Geral")

    val porDia = minutosPorDia()
    val porMateria = minutosPorMateria()
    val totalMinutos = porDia.sumOf { it.second }
    val maximoDia = porDia.maxOf { it.second }.coerceAtLeast(1)
    val totalMateria = porMateria.sumOf { it.second }.coerceAtLeast(1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Bege)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Seu progresso",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = VerdeEscuro
        )
        Text("Acompanhe sua evolução", color = TextoClaro, fontSize = 13.sp)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            abas.forEach { nome ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (nome == aba) VerdeEscuro else Color.White,
                    modifier = Modifier.clickable { aba = nome }
                ) {
                    Text(
                        text = nome,
                        color = if (nome == aba) Bege else TextoClaro,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Tempo de estudo", color = TextoClaro, fontSize = 11.sp)
                Text(
                    text = formatarTempo(totalMinutos),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdeEscuro
                )
                Text("18% vs semana passada", color = VerdeClaro, fontSize = 11.sp)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    porDia.forEach { (dia, minutos) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                Modifier
                                    .width(18.dp)
                                    .height((minutos * 100 / maximoDia).dp.coerceAtLeast(4.dp))
                                    .background(
                                        if (dia == "Qua") VerdeEscuro else VerdeClaro,
                                        RoundedCornerShape(4.dp)
                                    )
                            )
                            Text(dia, fontSize = 10.sp, color = TextoClaro)
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Matérias", fontWeight = FontWeight.Bold, color = VerdeEscuro)
                Text("Tempo por matéria", color = TextoClaro, fontSize = 11.sp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .padding(top = 12.dp)
                ) {
                    porMateria.forEach { (nome, minutos) ->
                        Box(
                            Modifier
                                .weight(minutos.toFloat())
                                .fillMaxWidth()
                                .height(14.dp)
                                .background(Color(corDaMateria(nome)))
                        )
                    }
                }

                Column(Modifier.padding(top = 12.dp)) {
                    porMateria.forEach { (nome, minutos) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .size(10.dp)
                                    .background(Color(corDaMateria(nome)), RoundedCornerShape(5.dp))
                            )
                            Text(
                                text = nome,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f).padding(start = 8.dp)
                            )
                            Text(
                                text = "${minutos}m (${minutos * 100 / totalMateria}%)",
                                fontSize = 12.sp,
                                color = TextoClaro
                            )
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Sessões concluídas", color = TextoClaro, fontSize = 11.sp)
                    Text(
                        text = sessoes.count { it.concluida }.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeEscuro
                    )
                    Text("20% vs semana passada", color = VerdeClaro, fontSize = 11.sp)
                }
                Box(
                    Modifier
                        .size(32.dp)
                        .background(VerdeClaro, RoundedCornerShape(16.dp))
                )
            }
        }
    }
}
