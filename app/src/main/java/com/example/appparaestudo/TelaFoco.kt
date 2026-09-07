package com.example.appparaestudo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appparaestudo.ui.theme.Bege
import com.example.appparaestudo.ui.theme.VerdeClaro
import com.example.appparaestudo.ui.theme.VerdeEscuro
import com.example.appparaestudo.ui.theme.VerdeMedio
import java.util.Date

@Composable
fun TelaFoco(
    materiaInicial: String,
    aoVoltar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val duracaoMs = 25L * 60L * 1000L

    var inicio by remember { mutableStateOf<Long?>(null) }
    var acumulado by remember { mutableLongStateOf(0L) }
    var tick by remember { mutableIntStateOf(0) }

    val rodando = inicio != null
    val emAndamento = if (inicio != null) Date().time - inicio!! else 0L
    val decorrido = acumulado + emAndamento + tick * 0L
    val restante = if (duracaoMs - decorrido > 0L) duracaoMs - decorrido else 0L
    val minutos = (restante / 60000L).toInt()
    val segundos = ((restante % 60000L) / 1000L).toInt()
    val porcentagem = ((decorrido * 100L) / duracaoMs).toInt()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VerdeEscuro)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "<",
                color = Bege,
                fontSize = 22.sp,
                modifier = Modifier.clickable { aoVoltar() }
            )
            Text(
                text = "  Sessao de estudo",
                color = Bege
            )
        }

        Text(
            text = materiaInicial,
            color = VerdeClaro,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .size(220.dp)
                .border(6.dp, VerdeClaro, RoundedCornerShape(110.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$minutos:$segundos",
                    color = Bege,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (rodando) "Foque!" else "Pausado",
                    color = VerdeClaro,
                    fontSize = 13.sp
                )
                Text(
                    text = "$porcentagem% da sessao",
                    color = VerdeClaro,
                    fontSize = 11.sp
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = {
                    if (inicio != null) {
                        acumulado = acumulado + (Date().time - inicio!!)
                        inicio = null
                    } else {
                        inicio = Date().time
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeMedio)
            ) {
                Text(text = if (rodando) "Pausar" else "Iniciar")
            }

            Button(
                onClick = { tick = tick + 1 },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeMedio)
            ) {
                Text(text = "Atualizar")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = VerdeMedio)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Objetivo da sessao",
                    color = VerdeClaro,
                    fontSize = 11.sp
                )
                Text(
                    text = "Entender os conceitos e resolver exercicios.",
                    color = Bege,
                    fontSize = 13.sp
                )
            }
        }

        Button(
            onClick = {
                if (inicio != null) {
                    acumulado = acumulado + (Date().time - inicio!!)
                    inicio = null
                }
                aoVoltar()
            },
            colors = ButtonDefaults.buttonColors(containerColor = VerdeMedio),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Encerrar sessao")
        }
    }
}
