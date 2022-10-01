package br.com.temperatureapp.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.temperatureapp.domain.Temperature
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    nav: NavController,
    viewModel: HomeViewModel
){
    Scaffold(
        modifier = Modifier
            .background(Color(0xFF1E1E1E))
            .fillMaxSize()
            .padding(20.dp),
        backgroundColor = Color(0xFF1E1E1E),
    ) {
        HomeContent(
            viewModel,
            nav
        )
    }
}

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    nav: NavController
){
    val thermometer = viewModel.thermometer.collectAsState(initial = Temperature(0,"blank",0f,0f,"01/01/01","00:00:00"))
    var mainTem by remember{ mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
           Text(
               text = thermometer.value.localizacao,
               color = Color.White,
               style = MaterialTheme.typography.h4.copy(
                   color = Color.White, fontWeight= FontWeight.Bold
               )
           )
        }
        Spacer(modifier = Modifier.size(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .border(width = 3.dp, color = colorPicker(thermometer.value.temperatura), shape = CircleShape)
                    .clip(CircleShape)
                    .size(150.dp)
                    .background(Color(0xff344655)),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${thermometer.value.umidade}%",
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Umidade",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .border(width = 3.dp, color = colorPicker(thermometer.value.temperatura), shape = CircleShape)
                    .clip(CircleShape)
                    .size(150.dp)
                    .background(Color(0xff344655)),
                contentAlignment = Alignment.Center,
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${celsiusToKelvin(thermometer.value.temperatura)}",
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Kelvin",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .border(width = 6.dp, color = colorPicker(thermometer.value.temperatura), shape = CircleShape)
                    .clip(CircleShape)
                    .size(260.dp)
                    .background(Color(0xff344655))
                    .clickable { mainTem = !mainTem },
                contentAlignment = Alignment.Center,
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if(mainTem)"${thermometer.value.temperatura}º"
                                else celsiusToFahrenheit(thermometer.value.temperatura).toString(),
                        style = MaterialTheme.typography.h2.copy(
                            color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = if(mainTem) "Celsius" else "Fahrenheit",
                        style = MaterialTheme.typography.h4.copy(
                            color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Última atualização",
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White, fontWeight = FontWeight.Normal
                )
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .height(40.dp)
                    .width(160.dp)
                    .background(Color(0xFF1775bb)),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = thermometer.value.data,
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .height(40.dp)
                    .width(160.dp)
                    .background(Color(0xFF1775bb)),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = thermometer.value.horario,
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom
        ){
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(9.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF1775bb)
                ),
                onClick = { nav.navigate("detail") }
            ) {
                Text(
                    text = "Histórico",
                    style = MaterialTheme.typography.h6.copy(
                        color = Color.White, fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

fun colorPicker(celsius: Float): Color{
    return when(celsius){
        in 0f..14f -> Color(0xFF1775bb)
        in 14f..15f -> Color(0xFF17A8BB)
        in 15f..16f -> Color(0xFF17BBA2)
        in 16f..17f -> Color(0xFF27BB17)
        in 17f..18f -> Color(0xFF48BB17)
        in 18f..19f -> Color(0xFF6EBB17)
        in 19f..20f -> Color(0xFF8FBB17)
        in 20f..21f -> Color(0xFFB6BB17)
        in 21f..22f -> Color(0xFFBB9217)
        in 22f..23f -> Color(0xFFBB7117)
        in 23f..24f -> Color(0xFFBB5917)
        in 24f..25f -> Color(0xFFBB3017)
        in 25f..26f -> Color(0xFFBB1717)
        else -> Color(0xFF1775bb)
    }
}

fun celsiusToFahrenheit(celsius: Float): Int{
    return (celsius*1.8f).roundToInt()+32
}

fun celsiusToKelvin(celsius: Float): Float{
    return ((celsius+273.15f)*100f).roundToInt()/100f
}