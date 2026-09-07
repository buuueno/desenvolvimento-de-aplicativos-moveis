package com.example.appparaestudo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.appparaestudo.ui.theme.VerdeEscuro

@Composable
fun TelaSelecao(
    aoEscolher: (Materia) -> Unit,
    modifier: Modifier = Modifier
) {
    var busca by remember { mutableStateOf("") }
    val filtradas = materias.filter { it.nome.contains(busca, ignoreCase = true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Bege)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Escolha a matéria",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = VerdeEscuro,
            modifier = Modifier.padding(top = 24.dp)
        )
        Text("O que você quer estudar?", color = TextoClaro, fontSize = 13.sp)

        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            label = { Text("Buscar matéria") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items = filtradas, key = { it.id }) { materia ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { aoEscolher(materia) },
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(36.dp)
                                .background(
                                    Color(materia.corHex),
                                    RoundedCornerShape(10.dp)
                                )
                        )
                        Column(Modifier.weight(1f).padding(start = 12.dp)) {
                            Text(materia.nome, fontWeight = FontWeight.Bold)
                            Text(
                                text = "${materia.topicosPendentes} tópicos pendentes",
                                color = TextoClaro,
                                fontSize = 12.sp
                            )
                        }
                        Text(">", color = TextoClaro)
                    }
                }
            }
        }
    }
}
