package br.com.temperatureapp.presentation.detail

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.temperatureapp.domain.Temperature
import java.util.*


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
        Column() {
            Filter(viewModel)
            DetailList(viewModel)
        }
    }
}

@Composable
private fun Filter(viewModel: DetailViewModel){

    val mContext = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val dateInitial = remember {
        mutableStateOf("")
    }

    val dateFinal = remember {
        mutableStateOf("")
    }

    val datePickerDialogFinal = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            dateFinal.value = formatString(year,month,day)
        }, year, month, day
    )

    val datePickerDialogInitial = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            dateInitial.value = formatString(year,month,day)
        }, year, month, day
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f),
        verticalArrangement = Arrangement.Top
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier.padding(end = 5.dp),
                onClick = { datePickerDialogInitial.show()
                          Log.e("date", "Date: $datePickerDialogInitial\n\n\nContext: $mContext")},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1775bb))
            ) {
                Text(
                    text = "Selecione Data Inicial",
                    fontSize = 13.sp,
                    color = Color.White
                    )
            }
            Button(
                modifier = Modifier.padding(start = 5.dp),
                onClick = { datePickerDialogFinal.show() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1775bb))
            )
            {
                Text(
                    text = "Selecione Data Final",
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                text = dateInitial.value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Até",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            Text(
                text = dateFinal.value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF1775bb),
                    contentColor = Color.White
                ),
                onClick = { viewModel.setFilter(dateInitial.value,dateFinal.value) },
                enabled = dateFinal.value.isNotEmpty()&&dateInitial.value.isNotEmpty()
            ) {
                Text(
                    text = "Filtrar",
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
private fun DetailList(
    viewModel: DetailViewModel
){
    val temps = viewModel.values.collectAsState(initial = listOf())
    LazyColumn(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()){
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
                    text = "Data: ${temp.data.substring(0,10)}",
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
                    Text(text = "Temperatura: ${temp.temperatura}", style = style)
                    Text(text = "Data: ${temp.data}", style = style)
                }
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .weight(0.55f)
                ) {
                    Text(text = "Umidade: ${temp.umidade}", style = style)
                    Text(text = "Hora: ${temp.horario}", style = style)
                }
            }
        }
    }
}

fun formatString(year: Int, month: Int, day: Int):String{
    var finalString = ""

    var updateMonth = month+1
    finalString += if(year.toString().toCharArray().size==1){
        "0$year-"
    }else{
        "$year-"
    }

    finalString += if(updateMonth.toString().toCharArray().size==1){
        "0$updateMonth-"
    }else{
        "$updateMonth-"
    }

    finalString += if(day.toString().toCharArray().size==1){
        "0$day"
    }else{
        day.toString()
    }

    return finalString
}
