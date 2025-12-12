    package com.example.residente_app.ui.screens.admin

    import androidx.compose.runtime.Composable
    import com.example.residente_app.viewmodel.ResidenceViewModel


    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.text.BasicTextField
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Mic
    import androidx.compose.material.icons.filled.Person
    import androidx.compose.material.icons.filled.Search
    import androidx.compose.material.icons.filled.Send
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.residente_app.data.remote.DTO.ResidenceResponse
    import com.example.residente_app.ui.components.search.EmptySearchResult
    import com.example.residente_app.ui.theme.AppColors
    import kotlinx.coroutines.launch

    @Composable
    fun HouseManageScreen(
        vm: ResidenceViewModel,
        onHouseRedirect:(String) -> Unit
    ){

        var search by remember { mutableStateOf("") };
        val houses by vm.houses.collectAsState()

        LaunchedEffect(Unit) {
            vm.getHouses()
        }

        val filteredList = houses.filter{
            it.identifier.contains(search, ignoreCase = true)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0D0D))
                .padding(16.dp)
        ) {

            HeaderSection()

            DescriptionSection()

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar(
                value = search,
                onValueChange = { search = it }
            )
            Spacer(modifier = Modifier.height(18.dp))

            HousesList(
                filteredList,
                onHouseRedirect={identifier -> onHouseRedirect(identifier)}
            )
        }
    }

    @Composable
    fun HeaderSection() {
        Text(
            "Domicilios",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

    @Composable
    fun DescriptionSection() {
        Text(
            "Consulta todos los domicilios registrados y revisa si actualmente poseen residentes asignados.",
            fontSize = 15.sp,
            color = Color(0xFFBBBBBB),
            modifier = Modifier.padding(top = 6.dp)
        )
    }

    @Composable
    fun SearchBar(
        value: String,
        onValueChange: (String) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFFFFFFF))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    decorationBox = { inner ->
                        if (value.isEmpty()) {
                            Text("Buscar domicilio...", color = Color.Gray)
                        }
                        inner()
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF444444),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    @Composable
    fun HousesList(
        houses: List<ResidenceResponse>,
        onHouseRedirect:(String) -> Unit
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if(houses.size > 0){
                houses.forEach { house ->
                    HouseCard(
                        house,
                        onHouseRedirect = onHouseRedirect
                    )
                }
            }else{
                EmptySearchResult (
                    description = "No existen residencias registradas que encajen con la búsqueda"
                ){  }
            }
        }
    }

    @Composable
    fun HouseCard(
        house: ResidenceResponse,
        onHouseRedirect:(String) -> Unit
    ) {

        val residentsCount = house.residents.size
        val hasResidents = residentsCount > 0

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.TextPrimary)
                .padding(horizontal = 16.dp, vertical = 10.dp),
                onClick = {onHouseRedirect(house.identifier)}
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = house.identifier,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = if (hasResidents) "Con residentes" else "Sin residentes",
                        color = if (hasResidents) Color(0xFF4CAF50) else Color(0xFFFF5252),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        contentDescription = "residentIcon",
                        imageVector = Icons.Default.Person,
                        tint = AppColors.Background,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = residentsCount.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }