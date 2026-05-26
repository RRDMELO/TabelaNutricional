# 🥗 Tabela Nutricional

<div align="center">

![License](https://img.shields.io/badge/License-MIT-green.svg)
![Android](https://img.shields.io/badge/Android-7.0+-blue.svg?logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-4285F4.svg?logo=android)
![Gradle](https://img.shields.io/badge/Gradle-8.0+-blue.svg?logo=gradle)
![Status](https://img.shields.io/badge/Status-In%20Development-yellow.svg)
![Maintenance](https://img.shields.io/badge/Maintenance-Active-green.svg)
![Platform](https://img.shields.io/badge/Platform-Android-success.svg?logo=android)

</div>

Um aplicativo Android moderno construído com **Jetpack Compose** que permite aos usuários buscar, visualizar e gerenciar receitas com informações nutricionais detalhadas.

## 📋 Sumário

- [Descrição do Projeto](#descrição-do-projeto)
- [Features](#features)
- [Melhorias Implementadas](#melhorias-implementadas)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Uso](#uso)
- [Licença](#licença)
- [Sobre](#sobre)

---

## 📱 Descrição do Projeto

**Tabela Nutricional** é um aplicativo Android completo para gerenciamento de receitas com foco em nutrição. O aplicativo permite que os usuários:

- 🔍 **Buscer receitas** por nome e filtros nutricionais avançados
- ⭐ **Marcar receitas como favoritas** para acesso rápido
- 📊 **Visualizar informações nutricionais** detalhadas (calorias, proteínas, carboidratos, fibras, etc.)
- 🍽️ **Categorizar refeições** por tipo (café da manhã, almoço, lanche, jantar)
- 💾 **Salvar receitas** localmente no dispositivo
- 🎨 **Interface moderna** com design responsivo e intuitivo

O aplicativo foi desenvolvido utilizando **Jetpack Compose** com padrões de arquitetura moderna, incluindo MVVM, Dependency Injection com Koin, e banco de dados Room.

---

## ✨ Features

<div align="center">

![Autenticação](https://img.shields.io/badge/✅-Autenticação-green.svg?style=flat)
![Busca](https://img.shields.io/badge/✅-Busca%20Real--time-green.svg?style=flat)
![Filtros](https://img.shields.io/badge/✅-Filtros%20Avançados-green.svg?style=flat)
![Favoritos](https://img.shields.io/badge/✅-Favoritos-green.svg?style=flat)
![Histórico](https://img.shields.io/badge/✅-Histórico-green.svg?style=flat)
![Responsivo](https://img.shields.io/badge/✅-Responsivo-green.svg?style=flat)
![Database](https://img.shields.io/badge/✅-Room%20DB-green.svg?style=flat)
![Imagens](https://img.shields.io/badge/✅-Imagens%20Otimizadas-green.svg?style=flat)

</div>

### Funcionalidades Principais
- ✅ Autenticação e gerenciamento de usuários
- ✅ Busca de receitas em tempo real
- ✅ Sistema avançado de filtros nutricionais
- ✅ Visualização detalhada de receitas
- ✅ Salvamento de receitas favoritas
- ✅ Histórico de receitas consultadas
- ✅ Interface responsiva em Jetpack Compose
- ✅ Banco de dados local com Room
- ✅ Imagens otimizadas e carregamento eficiente

---

## 🚀 Melhorias Implementadas

### 1. **Migração de Imagens Remotas para Locais** 📦
   - **Problema**: Imagens de receitas não carregavam da API Unsplash
   - **Solução**: Migração para recursos drawable locais
   - **Benefícios**:
     - ⚡ Carregamento instantâneo sem dependência de internet
     - 🔒 Melhor confiabilidade
     - 📉 Redução de requisições HTTP
     - 💾 Tamanho de build otimizado

### 2. **Otimização da Tela de Busca de Receitas** 🎨
   - **Implementação**:
     - Priorização inteligente de imagens (local → remoto → fallback)
     - Componente `RecipeImagePlaceholder()` para fallback visual
     - Logging detalhado com callbacks de sucesso/erro/carregamento
   - **Benefícios**:
     - 🚀 Performance melhorada
     - 🐛 Diagnóstico facilitado
     - 👁️ UI mais consistente

### 3. **Correções de Compilação e Lint** ✅
   - Correção de imports inválidos (typos)
   - Remoção de imports duplicados
   - Eliminação de variáveis não utilizadas
   - Build clean sem warnings de erro

### 4. **Data Layer Melhorado** 💾
   - SavedRecipe com suporte dual para imagens:
     - `imageResId` para recursos locais
     - `imageUrl` para URLs remotas
   - Sistema de priorização inteligente

### 5. **Pattern de Fallback Implementado** 🔄
   - **Priorização**: `imageResId` (local) → `imageUrl` (remoto) → `Placeholder` (emoji)
   - **Componente Reutilizável**: `RecipeImagePlaceholder()` com UI consistente
   - **Logging Detalhado**: 
     - ✅ Sucesso no carregamento
     - ❌ Erros reportados
     - ⏳ Estados de carregamento

---

## 🛠️ Tecnologias Utilizadas

<div align="center">

**Framework & UI**

![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-4285F4.svg?style=flat&logo=android)
![Material Design](https://img.shields.io/badge/Material%20Design%203-Latest-1f6feb.svg?style=flat)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg?style=flat&logo=kotlin)

**Arquitetura & Gerenciamento de Estado**

![MVVM](https://img.shields.io/badge/MVVM-Architecture-blue.svg?style=flat)
![Koin](https://img.shields.io/badge/Koin-DI-red.svg?style=flat)
![StateFlow](https://img.shields.io/badge/StateFlow-Reactive-green.svg?style=flat)

**Persistência & Backend**

![Room](https://img.shields.io/badge/Room-Database-blue.svg?style=flat&logo=android)
![SQLite](https://img.shields.io/badge/SQLite-Database-003b57.svg?style=flat&logo=sqlite)
![Datastore](https://img.shields.io/badge/Datastore-Preferences-4285F4.svg?style=flat)

**Integração & Imagens**

![Coil](https://img.shields.io/badge/Coil-Image%20Loading-FF6B6B.svg?style=flat)
![Drawable Resources](https://img.shields.io/badge/Drawable%20Resources-Local%20Images-success.svg?style=flat)

**Build & CI/CD**

![Gradle](https://img.shields.io/badge/Gradle-8.0+-blue.svg?style=flat&logo=gradle)
![Android Studio](https://img.shields.io/badge/Android%20Studio-2023.1+-green.svg?style=flat&logo=android)

</div>

### Componentes Principais

### Framework & UI
- **Jetpack Compose** - UI moderna declarativa
- **Material Design 3** - Design system
- **Kotlin** - Linguagem principal

### Arquitetura & State Management
- **MVVM** - Model-View-ViewModel
- **Koin** - Dependency Injection
- **StateFlow** - Reactive state management

### Persistência de Dados
- **Room Database** - Banco de dados local SQLite
- **Datastore** - Preferências do usuário

### Integração & Imagens
- **Coil** - Carregamento de imagens
- **AsyncImage** - Componente de imagens assíncronos
- **Drawable Resources** - Imagens locais otimizadas

### Build & CI/CD
- **Gradle** - Build system
- **Gradle Wrapper** - Versionamento de Gradle

---

## 📋 Requisitos

<div align="center">

![Android Studio](https://img.shields.io/badge/Android%20Studio-2023.1+-green.svg?style=flat&logo=android)
![Android SDK](https://img.shields.io/badge/SDK-API%2024+-blue.svg?style=flat&logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg?style=flat&logo=kotlin)
![Gradle](https://img.shields.io/badge/Gradle-8.0+-blue.svg?style=flat&logo=gradle)
![Java](https://img.shields.io/badge/JDK-11+-orange.svg?style=flat&logo=java)

</div>

### Sistema
- **Android Studio** 2023.1 ou superior
- **Android SDK** API 24 (Android 7.0) ou superior
- **Kotlin** 1.9+
- **Gradle** 8.0+

### Dependências
- AndroidX libraries (Compose, Room, Datastore)
- Jetpack Compose BOM
- Koin
- Coil
- Material3

---

## 💻 Instalação

### Clone o Repositório
```bash
git clone https://github.com/seu-usuario/TabelaNutricional.git
cd TabelaNutricional
```

### Configure o Projeto
```bash
# Atualize as dependências
./gradlew build

# Sincronize o projeto
./gradlew sync
```

### Execute no Emulador
```bash
# Instale o app em debug
./gradlew installDebug

# Ou execute diretamente
./gradlew runDebug
```

### Build de Release
```bash
# Crie o APK de release
./gradlew assembleRelease

# Ou gere o Bundle (Google Play)
./gradlew bundleRelease
```

---

## 📖 Uso

### Navegação Principal
1. **Tela Inicial (Home)**
   - Acesso rápido a receitas favoritas
   - Notícias nutricionais

2. **Busca de Receitas**
   - Campo de busca por nome
   - Filtros avançados por macronutrientes
   - Filtro por tipo de refeição

3. **Detalhes da Receita**
   - Informações nutricionais completas
   - Modo de preparo (quando disponível)
   - Marcar como favorita

4. **Minhas Receitas**
   - Receitas salvas localmente
   - Gerenciamento de favoritos
   - Histórico de consultas

### Fluxo de Busca
```
Home → Buscar Receitas → Aplicar Filtros → Selecionar Receita → Visualizar Detalhes → Salvar
```

---

## 📂 Estrutura do Projeto

```
TabelaNutricional/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/rocketseat/RRM/tabelanutricional/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screen/
│   │   │   │   │   │   ├── home/
│   │   │   │   │   │   ├── recipe_search/
│   │   │   │   │   │   ├── recipe_details/
│   │   │   │   │   │   └── my_recipes/
│   │   │   │   │   └── components/
│   │   │   │   ├── viewmodel/
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/
│   │   │   │   │   ├── dao/
│   │   │   │   │   └── database/
│   │   │   │   └── MainApplication.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   │   │   └── ...
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── .gitignore
├── README.md
└── local.properties (não versionado)
```

---

## 🧪 Testes

### Executar Testes Unitários
```bash
./gradlew test
```

### Executar Testes de Integração
```bash
./gradlew connectedAndroidTest
```

### Gerar Relatório de Coverage
```bash
./gradlew testDebugUnitTestCoverage
```

---

## 🔧 Desenvolvimento

### Adicionar Nova Feature
1. Crie uma branch: `git checkout -b feature/nova-feature`
2. Implemente a feature
3. Execute testes: `./gradlew test`
4. Commit: `git commit -am 'Add nova feature'`
5. Push: `git push origin feature/nova-feature`

### Padrões de Código
- **Naming**: camelCase para variáveis, PascalCase para classes
- **Formatação**: 4 espaços de indentação
- **Composables**: PascalCase, fim com Scope do compose
- **ViewModel**: Sufixo `ViewModel`
- **Repository**: Sufixo `Repository`

---

## 📄 Licença

Este projeto está licenciado sob a **Licença MIT** - veja o arquivo [LICENSE](LICENSE) para detalhes.

### MIT License

```
MIT License

Copyright (c) 2024 Tabela Nutricional

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:

1. Faça um Fork do projeto
2. Crie uma Branch para sua Feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a Branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 🎓 Sobre

Este é um **projeto de estudo** desenvolvido como parte do programa de formação **[RocketSeat](https://rocketseat.com.br/)**, uma plataforma educacional de excelência na área de tecnologia e desenvolvimento de software.

O projeto serve como complemento prático aos estudos de desenvolvimento Android, aplicando conceitos modernos de:
- Arquitetura de software
- Jetpack Compose e UI declarativa
- Padrões de design (MVVM, Repository)
- Gerenciamento de estado reativo
- Boas práticas de desenvolvimento

---

![RocketSeat](https://img.shields.io/badge/Desenvolvido%20em-RocketSeat-red.svg?style=for-the-badge)
![Study Project](https://img.shields.io/badge/Tipo-Projeto%20de%20Estudo-blue.svg?style=for-the-badge)
[![GitHub](https://img.shields.io/badge/GitHub-Repository-black.svg?style=for-the-badge&logo=github)](https://github.com/seu-usuario/TabelaNutricional)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0077B5.svg?style=for-the-badge&logo=linkedin)](https://linkedin.com)







