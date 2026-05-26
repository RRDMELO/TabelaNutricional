package com.rocketseat.RRM.tabelanutricional.ui.screen.recipe_search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

private val PrimaryGreen = Color(0xFF4CAF50)
private val PrimaryBlue = Color(0xFF0099FF)

@Composable
private fun FilterIconProgressive(
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(18.dp)
                .height(2.5.dp)
                .background(tint, shape = RoundedCornerShape(1.dp))
        )
        Box(
            modifier = Modifier
                .width(14.dp)
                .height(2.5.dp)
                .background(tint, shape = RoundedCornerShape(1.dp))
        )
        Box(
            modifier = Modifier
                .width(10.dp)
                .height(2.5.dp)
                .background(tint, shape = RoundedCornerShape(1.dp))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSearchScreen(
    viewModel: RecipeSearchViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onRecipeClick: (recipeId: String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .clickable(indication = null, interactionSource = null) {
                focusManager.clearFocus()
            }
    ) {
        // Header branco com borda azul
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        "Buscar receitas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(PrimaryBlue)
            )
        }

        // Conteúdo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de busca com botão de filtros
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .border(2.dp, PrimaryBlue, shape = RoundedCornerShape(12.dp))
                ) {
                    TextField(
                        value = state.searchQuery,
                        onValueChange = { query ->
                            viewModel.onEvent(RecipeSearchEvent.SearchQuery(query))
                        },
                        placeholder = {
                            Text(
                                "Salada",
                                color = Color.LightGray,
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                        singleLine = true
                    )
                }

                Button(
                    onClick = {
                        viewModel.onEvent(RecipeSearchEvent.ToggleFilters)
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .width(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    FilterIconProgressive(tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Painel de filtros
            if (state.showFilters) {
                FilterPanel(state, viewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Lista de receitas
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.recipes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhuma receita encontrada")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(indication = null, interactionSource = null) {
                            focusManager.clearFocus()
                        }
                ) {
                    items(state.recipes.size) { index ->
                        RecipeSearchItem(
                            state.recipes[index],
                            onRecipeClick = {
                                focusManager.clearFocus()
                                onRecipeClick(state.recipes[index].id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterPanel(
    state: RecipeSearchUIState,
    viewModel: RecipeSearchViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(2.dp, Color(0xFF4CAF50), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Campo de busca "Refeição"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .border(1.5.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        "Refeição",
                        color = Color(0xFFB0B0B0),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Linha divisória pontilhada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE8E8E8))
                    .padding(bottom = 0.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tipo de Refeição
            Text(
                "Tipo de refeição",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .align(Alignment.Start)
            )

            MealType.entries.forEach { mealType ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.selectedMealTypes.contains(mealType.name),
                        onCheckedChange = {
                            viewModel.onEvent(RecipeSearchEvent.ToggleMealType(mealType.name))
                        }
                    )
                    Text(
                        mealType.displayName,
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Linha divisória
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE8E8E8))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Macronutrientes
            Text(
                "Macronutrientes",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            SliderFilter(
                title = "Proteínas",
                label = "Alto",
                value = state.proteinRange,
                range = 0f..100f,
                onValueChange = { min, max ->
                    viewModel.onEvent(RecipeSearchEvent.UpdateProteinRange(min, max))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SliderFilter(
                title = "Carboidratos",
                label = "Médio",
                value = state.carbRange,
                range = 0f..200f,
                onValueChange = { min, max ->
                    viewModel.onEvent(RecipeSearchEvent.UpdateCarbRange(min, max))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SliderFilter(
                title = "Gorduras",
                label = "Baixo",
                value = state.fatRange,
                range = 0f..100f,
                onValueChange = { min, max ->
                    viewModel.onEvent(RecipeSearchEvent.UpdateFatRange(min, max))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SliderFilter(
                title = "Açúcares",
                label = "Baixo",
                value = state.sugarRange,
                range = 0f..50f,
                onValueChange = { min, max ->
                    viewModel.onEvent(RecipeSearchEvent.UpdateSugarRange(min, max))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão Filtrar
            Button(
                onClick = {
                    viewModel.onEvent(RecipeSearchEvent.ApplyFilters)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Filtrar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun SliderFilter(
    title: String,
    label: String = "",
    value: Pair<Float, Float>,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float, Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            if (label.isNotEmpty()) {
                Text(
                    label,
                    fontSize = 12.sp,
                    color = Color(0xFF999999)
                )
            }
        }

        Slider(
            value = value.first,
            onValueChange = { newValue ->
                if (newValue <= value.second) {
                    onValueChange(newValue, value.second)
                }
            },
            valueRange = range,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            colors = androidx.compose.material3.SliderDefaults.colors(
                thumbColor = PrimaryGreen,
                activeTrackColor = PrimaryGreen,
                inactiveTrackColor = Color(0xFFE0E0E0)
            )
        )

        Slider(
            value = value.second,
            onValueChange = { newValue ->
                if (newValue >= value.first) {
                    onValueChange(value.first, newValue)
                }
            },
            valueRange = range,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = androidx.compose.material3.SliderDefaults.colors(
                thumbColor = PrimaryGreen,
                activeTrackColor = PrimaryGreen,
                inactiveTrackColor = Color(0xFFE0E0E0)
            )
        )
    }
}

@Composable
private fun RecipeSearchItem(
    recipe: SavedRecipe,
    onRecipeClick: () -> Unit = {}
) {
    Log.d("RecipeSearchItem", "Loading recipe: ${recipe.name}, imageResId: ${recipe.imageResId}, imageUrl: ${recipe.imageUrl}")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(2.dp, PrimaryBlue, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable(indication = null, interactionSource = null) {
                onRecipeClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Imagem da receita
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(1.dp, PrimaryBlue, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Prioriza imageResId (local) em vez de imageUrl (remoto)
                if (recipe.imageResId != null) {
                    Image(
                        painter = painterResource(id = recipe.imageResId),
                        contentDescription = recipe.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Log.d("RecipeSearchItem", "✅ Loading local image from drawable for: ${recipe.name}")
                } else if (!recipe.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = recipe.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop,
                        onError = {
                            Log.e("RecipeSearchItem", "❌ Error loading remote image for: ${recipe.name}")
                        },
                        onLoading = {
                            Log.d("RecipeSearchItem", "⏳ Loading remote image for: ${recipe.name}")
                        },
                        onSuccess = {
                            Log.d("RecipeSearchItem", "✅ Successfully loaded remote image for: ${recipe.name}")
                        }
                    )
                } else {
                    Log.d("RecipeSearchItem", "No image available for: ${recipe.name}")
                    RecipeImagePlaceholder()
                }
            }

            // Informações da receita
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    recipe.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "${String.format("%.2f", recipe.calories)} kcal",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "${String.format("%.2f", recipe.proteins)}g proteínas, ${String.format("%.2f", recipe.carbohydrates)}g carbo...",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecipeSearchScreenPreview() {
    val mockRecipes = listOf(
        SavedRecipe(
            name = "Salada variada",
            calories = 221.15f,
            proteins = 15.13f,
            carbohydrates = 18.40f,
            fiber = 5f,
            mealType = "LUNCH"
        ),
        SavedRecipe(
            name = "Frango grelhado",
            calories = 320.45f,
            proteins = 30.25f,
            carbohydrates = 22.80f,
            fiber = 0f,
            mealType = "LUNCH"
        ),
        SavedRecipe(
            name = "Omelete de queijo e espinaf...",
            calories = 280.10f,
            proteins = 20.50f,
            carbohydrates = 10.30f,
            fiber = 2f,
            mealType = "BREAKFAST"
        ),
        SavedRecipe(
            name = "Panqueca de aveia e banana",
            calories = 250.60f,
            proteins = 8.75f,
            carbohydrates = 40.20f,
            fiber = 4f,
            mealType = "BREAKFAST"
        ),
        SavedRecipe(
            name = "Tofu grelhado",
            calories = 221.15f,
            proteins = 15.13f,
            carbohydrates = 18.40f,
            fiber = 3f,
            mealType = "LUNCH"
        ),
        SavedRecipe(
            name = "Iogurte natural comgranola",
            calories = 190.30f,
            proteins = 12.10f,
            carbohydrates = 30.15f,
            fiber = 2f,
            mealType = "BREAKFAST"
        )
    )

    RecipeSearchScreenPreviewContent(mockRecipes = mockRecipes)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSearchScreenPreviewContent(mockRecipes: List<SavedRecipe>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        // Header branco com borda azul
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        "Buscar receitas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(PrimaryBlue)
            )
        }

        // Conteúdo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de busca com botão de filtros
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .border(2.dp, PrimaryBlue, shape = RoundedCornerShape(12.dp))
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = {
                            Text(
                                "Salada",
                                color = Color.LightGray,
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                        singleLine = true
                    )
                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .height(48.dp)
                        .width(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    FilterIconProgressive(tint = Color.White)
                }
            }

            // Lista de receitas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(mockRecipes) { recipe ->
                    RecipeSearchItem(recipe) {}
                }
            }
        }
    }
}

@Composable
private fun RecipeImagePlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .background(
                color = Color(0xFFF0F0F0),
                shape = RoundedCornerShape(12.dp)
            )
            .border(1.dp, PrimaryBlue, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "🥗",
            fontSize = 36.sp
        )
    }
}
