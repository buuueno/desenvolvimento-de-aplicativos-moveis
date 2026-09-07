package com.example.appparaestudo

data class Materia(
    val id: Int,
    val nome: String,
    val topicosPendentes: Int,
    val corHex: Long
)

data class Sessao(
    val id: Int,
    val materia: String,
    val topico: String,
    val horario: String,
    val minutos: Int,
    val dia: Int,
    val concluida: Boolean
)

val materias = listOf(
    Materia(1, "Matemática", 5, 0xFF2E7D5B),
    Materia(2, "Física", 4, 0xFF7E57C2),
    Materia(3, "Química", 3, 0xFF1E88E5),
    Materia(4, "História", 2, 0xFFC62828),
    Materia(5, "Biologia", 3, 0xFF388E3C),
    Materia(6, "Português", 4, 0xFFF9A825)
)

val sessoes = listOf(
    Sessao(1, "Matemática", "Funções do 1º grau", "09:30", 45, 16, true),
    Sessao(2, "Física", "Leis de Newton", "11:00", 45, 16, true),
    Sessao(3, "Química", "Ligações químicas", "15:30", 45, 16, false),
    Sessao(4, "Revisão", "Flashcards e exercícios", "17:00", 30, 16, false),
    Sessao(5, "Matemática", "Equações", "09:30", 60, 14, true),
    Sessao(6, "Biologia", "Citologia", "14:00", 50, 15, true),
    Sessao(7, "Português", "Interpretação", "10:00", 40, 17, false),
    Sessao(8, "Física", "Cinemática", "16:00", 55, 18, false)
)

val diasSemana = listOf(
    14 to "Seg", 15 to "Ter", 16 to "Qua", 17 to "Qui",
    18 to "Sex", 19 to "Sáb", 20 to "Dom"
)

fun minutosPorDia(): List<Pair<String, Int>> {
    return diasSemana.map { (numero, nome) ->
        nome to sessoes.filter { it.dia == numero }.sumOf { it.minutos }
    }
}

fun minutosPorMateria(): List<Pair<String, Int>> {
    return sessoes.groupBy { it.materia }
        .map { (nome, lista) -> nome to lista.sumOf { it.minutos } }
        .sortedByDescending { it.second }
}

fun corDaMateria(nome: String): Long {
    return materias.find { it.nome == nome }?.corHex ?: 0xFF9E9E9E
}

fun formatarTempo(minutos: Int): String {
    return "${minutos / 60}h ${minutos % 60}m"
}
