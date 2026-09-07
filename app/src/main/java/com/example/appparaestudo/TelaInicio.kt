package com.example.appparaestudo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun TelaInicio(modifier: Modifier = Modifier) {
    val totalPlanejado = sessoes.filter { it.dia == 16 }.sumOf { it.minutos }
    val metaDiaria = 220
    val progresso = totalPlanejado.toFloat() / metaDiaria

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Bege)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Bom dia, João", color = TextoClaro, fontSize = 14.sp)

        Text(
            text = "Um bom plano\ncomeça com\numa escolha.",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = VerdeEscuro
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = VerdeEscuro)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("FOCO DE HOJE", color = VerdeClaro, fontSize = 11.sp)
                Text(
                    text = formatarTempo(totalPlanejado),
                    color = Bege,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("Tempo total planejado", color = VerdeClaro, fontSize = 12.sp)

                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Color(0x33FFFFFF), RoundedCornerShape(3.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progresso)
                            .height(6.dp)
                            .background(VerdeClaro, RoundedCornerShape(3.dp))
                    )
                }

                Text(
                    text = "${(progresso * 100).toInt()}% da meta diária",
                    color = VerdeClaro,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Text("PRÓXIMA SESSÃO", color = TextoClaro, fontSize = 11.sp)

        val proxima = sessoes.first { it.dia == 16 && !it.concluida }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .height(36.dp)
                        .background(Color(corDaMateria(proxima.materia)), RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp)
                )
                Column(Modifier.weight(1f).padding(start = 12.dp)) {
                    Text(proxima.materia, fontWeight = FontWeight.Bold)
                    Text(proxima.topico, color = TextoClaro, fontSize = 12.sp)
                }
                Text(proxima.horario, color = TextoClaro, fontSize = 13.sp)
            }
        }

        Text("RESUMO DO DIA", color = TextoClaro, fontSize = 11.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ResumoItem(sessoes.count { it.dia == 16 }.toString(), "Sessões", Modifier.weight(1f))
            ResumoItem(formatarTempo(totalPlanejado), "Tempo", Modifier.weight(1f))
            ResumoItem("8", "Tarefas", Modifier.weight(1f))
        }
    }
}

@Composable
fun ResumoItem(valor: String, rotulo: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(valor, fontWeight = FontWeight.Bold)
            Text(rotulo, color = TextoClaro, fontSize = 11.sp)
        }
    }
}
