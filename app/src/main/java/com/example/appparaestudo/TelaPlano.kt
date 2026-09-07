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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appparaestudo.ui.theme.Bege
import com.example.appparaestudo.ui.theme.TextoClaro
import com.example.appparaestudo.ui.theme.VerdeEscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPlano(modifier: Modifier = Modifier) {
    var diaSelecionado by remember { mutableIntStateOf(16) }
    var mostrarForm by remember { mutableStateOf(false) }
    val lista = remember { mutableStateListOf(*sessoes.toTypedArray()) }

    var materiaNova by remember { mutableStateOf(materias.first().nome) }
    var expandido by remember { mutableStateOf(false) }
    var horaNova by remember { mutableStateOf("") }
    var duracaoNova by remember { mutableFloatStateOf(45f) }
    var ehRevisao by remember { mutableStateOf(false) }

    val doDia = lista.filter { it.dia == diaSelecionado }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Bege)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Plano de estudos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = VerdeEscuro,
            modifier = Modifier.padding(top = 24.dp)
        )
        Text("Organize sua semana", color = TextoClaro, fontSize = 13.sp)

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            diasSemana.forEach { (numero, nome) ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (numero == diaSelecionado) VerdeEscuro else Color.Transparent,
                    modifier = Modifier.clickable { diaSelecionado = numero }
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = numero.toString(),
                            color = if (numero == diaSelecionado) Bege else VerdeEscuro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = nome,
                            color = if (numero == diaSelecionado) Bege else TextoClaro,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = doDia, key = { it.id }) { sessao ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(sessao.horario, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("${sessao.minutos} min", color = TextoClaro, fontSize = 11.sp)
                        }
                        Box(
                            Modifier
                                .padding(start = 12.dp)
                                .size(30.dp)
                                .background(
                                    Color(corDaMateria(sessao.materia)),
                                    RoundedCornerShape(8.dp)
                                )
                        )
                        Column(Modifier.weight(1f).padding(start = 10.dp)) {
                            Text(sessao.materia, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(sessao.topico, color = TextoClaro, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        if (mostrarForm) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    ExposedDropdownMenuBox(
                        expanded = expandido,
                        onExpandedChange = { expandido = !expandido }
                    ) {
                        OutlinedTextField(
                            value = materiaNova,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Matéria") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandido) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = expandido,
                            onDismissRequest = { expandido = false }
                        ) {
                            materias.forEach { materia ->
                                DropdownMenuItem(
                                    text = { Text(materia.nome) },
                                    onClick = {
                                        materiaNova = materia.nome
                                        expandido = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = horaNova,
                        onValueChange = { horaNova = it },
                        label = { Text("Horário (ex: 0930)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    )

                    Text(
                        text = "Duração: ${duracaoNova.toInt()} min",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Slider(
                        value = duracaoNova,
                        onValueChange = { duracaoNova = it },
                        valueRange = 15f..120f,
                        steps = 6
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = ehRevisao, onCheckedChange = { ehRevisao = it })
                        Text("É revisão", fontSize = 13.sp)
                    }

                    Button(
                        onClick = {
                            lista.add(
                                Sessao(
                                    id = (lista.maxOf { it.id }) + 1,
                                    materia = materiaNova,
                                    topico = if (ehRevisao) "Revisão" else "Novo conteúdo",
                                    horario = horaNova.padStart(4, '0').let {
                                        "${it.take(2)}:${it.takeLast(2)}"
                                    },
                                    minutos = duracaoNova.toInt(),
                                    dia = diaSelecionado,
                                    concluida = false
                                )
                            )
                            horaNova = ""
                            mostrarForm = false
                        },
                        enabled = horaNova.length in 3..4,
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    ) {
                        Text("Salvar sessão")
                    }
                }
            }
        }

        Button(
            onClick = { mostrarForm = !mostrarForm },
            colors = ButtonDefaults.buttonColors(containerColor = VerdeEscuro),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(48.dp)
        ) {
            Text(if (mostrarForm) "Cancelar" else "+ Adicionar sessão")
        }
    }
}
