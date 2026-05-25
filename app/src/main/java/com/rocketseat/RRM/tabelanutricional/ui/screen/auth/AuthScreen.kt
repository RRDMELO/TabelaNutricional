package com.rocketseat.RRM.tabelanutricional.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.Surface
import androidx.compose.material3.AlertDialog
import java.io.File
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

private val PrimaryGreen = Color(0xFF4CAF50)
private val LightGreen = Color(0xFFC8E6C9)

@Composable
private fun ForkKnifeIcon(modifier: Modifier = Modifier, color: Color = Color.White) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            // Garfo (esquerda)
            val forkX = width * 0.2f
            // Cabo do garfo
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(forkX, height * 0.3f),
                end = androidx.compose.ui.geometry.Offset(forkX, height * 0.85f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
            // Dentes do garfo
            val prong1X = forkX - width * 0.08f
            val prong2X = forkX
            val prong3X = forkX + width * 0.08f
            val prongsTopY = height * 0.3f
            val prongsBottomY = height * 0.15f

            // Prong 1
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(prong1X, prongsTopY),
                end = androidx.compose.ui.geometry.Offset(prong1X, prongsBottomY),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
            // Prong 2
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(prong2X, prongsTopY),
                end = androidx.compose.ui.geometry.Offset(prong2X, prongsBottomY),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
            // Prong 3
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(prong3X, prongsTopY),
                end = androidx.compose.ui.geometry.Offset(prong3X, prongsBottomY),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )

            // Faca (direita)
            val knifeX = width * 0.8f
            // Cabo da faca
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(knifeX, height * 0.35f),
                end = androidx.compose.ui.geometry.Offset(knifeX, height * 0.85f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
            // Lâmina da faca
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(knifeX, height * 0.35f),
                end = androidx.compose.ui.geometry.Offset(knifeX + width * 0.1f, height * 0.1f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    var registerUsername by remember { mutableStateOf("") }
    var registerEmail by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }
    var profilePhotoUri by remember { mutableStateOf<String?>(null) }
    var showPhotoDialog by remember { mutableStateOf(false) }
    var cameraPhotoUri by remember { mutableStateOf<String?>(null) }

    // Launcher para capturar foto com câmera (DEVE SER DECLARADO PRIMEIRO)
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraPhotoUri != null) {
            profilePhotoUri = cameraPhotoUri
            cameraPhotoUri = null
        }
    }

    // Launcher para selecionar foto da galeria
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            profilePhotoUri = uri.toString()
        }
    }

    // Launcher para solicitar permissão de câmera
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permissão foi concedida, abrir câmera
            createPhotoFile(context).let { file ->
                val photoUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
                cameraPhotoUri = photoUri.toString()
                takePictureLauncher.launch(photoUri)
            }
        }
    }

    // Diálogo para escolher câmera ou galeria
    if (showPhotoDialog) {
        AlertDialog(
            onDismissRequest = { showPhotoDialog = false },
            title = { Text("Selecionar foto de perfil") },
            text = { Text("Escolha como deseja adicionar sua foto") },
            confirmButton = {
                Button(
                    onClick = {
                        // Verificar permissão de câmera
                        if (ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            // Permissão já concedida, abrir câmera
                            createPhotoFile(context).let { file ->
                                val photoUri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.fileprovider",
                                    file
                                )
                                cameraPhotoUri = photoUri.toString()
                                takePictureLauncher.launch(photoUri)
                            }
                        } else {
                            // Solicitar permissão
                            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                        showPhotoDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("📷 Câmera", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        pickImageLauncher.launch("image/*")
                        showPhotoDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("🖼️ Galeria", color = Color.White)
                }
            }
        )
    }

    if (state.isLoggedIn) {
        onLoginSuccess()
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header com logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGreen)
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(PrimaryGreen, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .size(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ForkKnifeIcon(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    }
                    Text(
                        text = "NutriChef",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Conteúdo (scrollável)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (!state.isRegistering) {
                    // Tela de Login
                    Text(
                        text = "Entrar",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Acesse suas receitas saudáveis favoritas",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp, top = 4.dp)
                    )

                    Text(
                        text = "E-mail",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("seu@mail.com", color = Color.LightGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Text(
                        text = "Senha",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Sua senha de acesso", color = Color.LightGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        singleLine = true,
                        visualTransformation = if (state.showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.onEvent(AuthEvent.ToggleShowPassword)
                            }) {
                                Icon(
                                    imageVector = if (state.showPassword) {
                                        Icons.Filled.Visibility
                                    } else {
                                        Icons.Filled.VisibilityOff
                                    },
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(checkedColor = PrimaryGreen)
                        )
                        Text(
                            "Continuar conectado",
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.onEvent(
                                AuthEvent.Login(email, password, rememberMe)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(end = 8.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }
                        Text("Acessar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    // Tela de Registro
                    Text(
                        text = "Criar Conta",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Cadastre-se para acessar receitas saudáveis",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp, top = 4.dp)
                    )

                    // Botão de Upload de Foto
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Foto de perfil",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Surface(
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    showPhotoDialog = true
                                },
                            shape = RoundedCornerShape(50),
                            color = PrimaryGreen
                        ) {
                            if (profilePhotoUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(profilePhotoUri),
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.CloudUpload,
                                        contentDescription = "Upload foto",
                                        tint = Color.White,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = registerUsername,
                        onValueChange = { registerUsername = it },
                        label = { Text("Nome") },
                        placeholder = { Text("Seu nome completo", color = Color.LightGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = registerEmail,
                        onValueChange = { registerEmail = it },
                        label = { Text("E-mail") },
                        placeholder = { Text("seu@mail.com", color = Color.LightGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    OutlinedTextField(
                        value = registerPassword,
                        onValueChange = { registerPassword = it },
                        label = { Text("Senha") },
                        placeholder = { Text("Sua senha de acesso", color = Color.LightGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        singleLine = true,
                        visualTransformation = if (state.showRegisterPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.onEvent(AuthEvent.ToggleShowRegisterPassword)
                            }) {
                                Icon(
                                    imageVector = if (state.showRegisterPassword) {
                                        Icons.Filled.Visibility
                                    } else {
                                        Icons.Filled.VisibilityOff
                                    },
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        }
                    )

                    Button(
                        onClick = {
                            viewModel.onEvent(
                                AuthEvent.Register(registerUsername, registerEmail, registerPassword)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(end = 8.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }
                        Text("Acessar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Erro
                if (!state.errorMessage.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.errorMessage ?: "",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(AuthEvent.ClearError)
                            }
                            .padding(8.dp)
                    )
                }
            }

            // Footer com toggle
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (state.isRegistering) {
                            "Já tem uma conta? "
                        } else {
                            "Não tem uma conta? "
                        },
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = if (state.isRegistering) "Entrar na conta" else "Criar conta",
                        fontSize = 14.sp,
                        color = PrimaryGreen,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            viewModel.toggleRegisterMode()
                            email = ""
                            password = ""
                            registerUsername = ""
                            registerEmail = ""
                            registerPassword = ""
                            profilePhotoUri = null
                            viewModel.onEvent(AuthEvent.ClearError)
                        }
                    )
                }
            }
        }
    }
}

fun createPhotoFile(context: Context): File {
    val timeStamp = System.currentTimeMillis()
    val storageDir = context.cacheDir
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}

