package com.example.appparaestudo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appparaestudo.ui.theme.AppParaestudoTheme
import com.example.appparaestudo.ui.theme.Bege
import com.example.appparaestudo.ui.theme.VerdeClaro
import com.example.appparaestudo.ui.theme.VerdeEscuro

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppParaestudoTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    var telaAtual by remember { mutableStateOf("inicio") }
    var materiaSelecionada by remember { mutableStateOf("Matematica") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(containerColor = VerdeEscuro) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ItemBarra("Inicio", "inicio", telaAtual) { telaAtual = it }
                    ItemBarra("Plano", "plano", telaAtual) { telaAtual = it }
                    ItemBarra("Foco", "foco", telaAtual) { telaAtual = it }
                    ItemBarra("Progresso", "progresso", telaAtual) { telaAtual = it }
                    ItemBarra("Mais", "mais", telaAtual) { telaAtual = it }
                }
            }
        }
    ) { innerPadding ->
        when (telaAtual) {
            "inicio" -> TelaInicio(Modifier.padding(innerPadding))
            "selecao" -> TelaSelecao(
                aoEscolher = { materia ->
                    materiaSelecionada = materia.nome
                    telaAtual = "foco"
                },
                modifier = Modifier.padding(innerPadding)
            )
            "foco" -> TelaFoco(
                materiaInicial = materiaSelecionada,
                aoVoltar = { telaAtual = "selecao" },
                modifier = Modifier.padding(innerPadding)
            )
            "progresso" -> TelaProgresso(Modifier.padding(innerPadding))
            "plano" -> TelaPlano(Modifier.padding(innerPadding))
            else -> TelaInicio(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ItemBarra(
    rotulo: String,
    destino: String,
    telaAtual: String,
    aoClicar: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { aoClicar(if (destino == "foco") "selecao" else destino) }
            .padding(4.dp)
    ) {
        Text(
            text = rotulo,
            color = if (telaAtual == destino) VerdeClaro else Bege,
            fontSize = 11.sp,
            fontWeight = if (telaAtual == destino) FontWeight.Bold else FontWeight.Normal
        )
    }
}
