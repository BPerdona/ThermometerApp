package br.com.temperatureapp.presentation.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.temperatureapp.domain.Temperature
import br.com.temperatureapp.presentation.home.HomeViewModel


@Composable
fun DetailScreen(
    viewModel: DetailViewModel
){
    Scaffold(
        modifier = Modifier
            .background(Color(0xFF1E1E1E))
            .fillMaxSize()
            .padding(10.dp),
        backgroundColor = Color(0xFF1E1E1E),
    ) {
        DetailList(viewModel)
    }
}

@Composable
private fun DetailList(
    viewModel: DetailViewModel
){
    val temps = viewModel.values.collectAsState(initial = listOf())
    LazyColumn(){
        items(temps.value.asReversed()){
            TemperatureCard(temp = it)
        }
    }
}

@Composable
private fun TemperatureCard(temp: Temperature){
    var expanded by remember{
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .padding(4.dp),
        backgroundColor = Color(0xFF284967)
    ) {
        Column(){
            Row(
                modifier = Modifier
                    .padding(7.dp)
                    .clickable { expanded = !expanded }
                    .animateContentSize(
                        spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.9f),
                    text = "Horário: ${temp.horario}",
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = Color(0xFFE9EDF0), fontWeight = FontWeight.Normal, fontSize = 18.sp
                    )
                )
                Icon(
                    modifier = Modifier
                        .weight(0.1f)
                        .size(30.dp)
                        .rotate(if (expanded) 180f else 0f),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "ArrowDown",
                    tint = Color.White
                )
            }
            if(expanded)
            Row(
                modifier = Modifier
                    .padding(start = 7.dp, bottom = 7.dp, end = 7.dp)
                    .background(Color(0xFF416280))
            ) {
                val style = MaterialTheme.typography.body2.copy(
                    color = Color.White, fontWeight = FontWeight.Normal
                )
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .weight(0.45f)
                ) {

                    Text(text = "Sensor: ${temp.sensorId}", style = style)
                    Text(text = "Temperatura: ${temp.temperatura}", style = style)
                    Text(text = "Data: ${temp.data}", style = style)
                }
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .weight(0.55f)
                ) {
                    Text(text = "Local: ${temp.localizacao}", style = style)
                    Text(text = "Umidade: ${temp.umidade}", style = style)
                    Text(text = "Hora: ${temp.horario}", style = style)
                }
            }
        }
    }
}
