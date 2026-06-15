# Testes Unitários (Camada Business Logic)

![Tests](https://img.shields.io/badge/tests-documented-brightgreen)
![Coverage](https://img.shields.io/badge/coverage-unit%20%2B%20ui-blue)
![Android](https://img.shields.io/badge/platform-android-3DDC84)

Este documento resume os testes já implementados no app e explica, de forma prática, os métodos de teste usados em cada camada.

## Visão geral

A suíte de testes está organizada em três níveis principais:

- **Testes unitários**: validam regras de negócio, repositórios e use cases sem depender da interface.
- **Testes de ViewModel**: validam estados, eventos e fluxo de dados das telas.
- **Testes de UI / snapshot**: validam comportamento visual e composição das telas.

---

## 1) Testes Unitários — Camada de Business Logic

### 1.1 Testes para `Use Cases` / `Interactors`

Os testes dessa camada verificam regras de negócio isoladas, usando fakes para simular dependências.

#### Arquivos prontos

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/domain/usecase/RegisterUseCaseTest.kt`
  - valida campos vazios
  - valida sintaxe básica de e-mail
  - valida usuário já existente
  - valida cadastro com dados corretos

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/domain/usecase/RecipeUseCasesTest.kt`
  - busca e filtro de receitas
  - leitura de receita por ID
  - verificação de favorito
  - atualização de favorito
  - login

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/domain/usecase/GetHomeContentUseCaseTest.kt`
  - valida a montagem do conteúdo da Home com receitas e notícias

#### Método de teste usado

- criação de **fakes** para repositórios
- uso de `runBlocking` / `runTest` quando necessário
- verificação de retorno e de chamadas feitas às dependências

---

### 1.2 Testes para `Repositórios`

Esses testes validam a implementação concreta dos repositórios, garantindo que eles façam a ponte correta entre domínio e fonte de dados.

#### Arquivos prontos

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/data/repository/UserRepositoryImplTest.kt`
  - hash de senha no cadastro
  - login com senha correta e incorreta
  - verificação de existência de usuário

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/data/repository/RecipeRepositoriesImplTest.kt`
  - operações com receitas salvas
  - busca por tipos de refeição
  - atualização de favorito
  - delegação para `HealthyRecipeRepositoryImpl`

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/data/repository/HomeContentRepositoryImplTest.kt`
  - combinação de listas de notícias e receitas para a Home

#### Método de teste usado

- criação de **DAOs/data sources fake**
- validação de delegação e transformação de dados
- coleta de `Flow` com `first()` quando necessário

---

## 2) Testes para `ViewModels`

Esses testes garantem que os eventos da UI alteram o estado corretamente e disparam os use cases esperados.

### Arquivos prontos

#### Em `androidTest`

- `app/src/androidTest/java/com/rocketseat/RRM/tabelanutricional/ui/screen/recipe_search/RecipeSearchViewModelTest.kt`
  - busca por texto
  - toggle de tipo de refeição
  - limpeza de filtros

- `app/src/androidTest/java/com/rocketseat/RRM/tabelanutricional/ui/screen/healthy_recipe_details/HealthyRecipeDetailsViewModelTest.kt`
  - carregamento de receita por ID
  - atualização de favorito
  - reset do estado ao voltar

#### Observação

Também existem versões em `app/src/test/...` marcadas com `@Ignore` para manter a suíte JVM limpa, mas a execução principal dos testes de `ViewModel` está em `androidTest`.

### Método de teste usado

- criação de **repositórios fake**
- uso de `MainDispatcherRule` para controlar `Dispatchers.Main`
- disparo de eventos com `onEvent(...)`
- verificação do `StateFlow` exposto pelo `ViewModel`
- validação de chamadas feitas aos use cases

#### Utilitário usado

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/MainDispatcherRule.kt`
- `app/src/androidTest/java/com/rocketseat/RRM/tabelanutricional/MainDispatcherRule.kt`

---

## 3) Testes de UI / Snapshot

Esses testes validam a tela em si, tanto visualmente quanto por interação.

### 3.1 Snapshot tests

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/ui/screen/HomeScreenSnapshotTest.kt`
  - snapshot do `HomeScreen` com dados mockados usando Paparazzi

### 3.2 Compose UI tests

- `app/src/androidTest/java/com/rocketseat/RRM/tabelanutricional/ui/screen/home/home/HomeScreenTest.kt`
  - estado de loading
  - estado com conteúdo carregado

- `app/src/androidTest/java/com/rocketseat/RRM/tabelanutricional/ui/screen/home/Healthy_recipe_details/HealthyRecipeDetailsScreenTest.kt`
  - botão de favorito
  - botão de mais detalhes
  - scroll para conteúdo

#### Método de teste usado

- `createComposeRule()`
- busca por `tag`, `text` e `contentDescription`
- simulação de clique e scroll
- snapshots visuais para evitar regressão de layout

---

## 4) Testes exemplo já prontos

Esses testes já existiam no projeto e continuam disponíveis como referência:

- `app/src/test/java/com/rocketseat/RRM/tabelanutricional/ExampleUnitTest.kt`
- `app/src/androidTest/java/com/rocketseat/RRM/tabelanutricional/ExampleInstrumentedTest.kt`

---

## 5) Resumo rápido da cobertura atual

### Business Logic
- Use cases: **sim**
- Repositórios: **sim**
- Validação de cadastro: **sim**

### UI / ViewModel
- `HomeViewModel`: **coberto indiretamente pelos testes de tela e snapshot**
- `RecipeSearchViewModel`: **sim**
- `HealthyRecipeDetailsViewModel`: **sim**
- `AuthViewModel`: **ainda não há teste dedicado pronto**

### UI da aplicação
- `HomeScreen`: **sim**
- `HealthyRecipeDetailsScreen`: **sim**

---

## 6) Estrutura recomendada

- `app/src/test` → lógica de negócio e testes rápidos de JVM
- `app/src/androidTest` → Compose UI, ViewModels dependentes de Android e fluxos instrumentados
- `MainDispatcherRule` → controle de corrotinas nos testes de ViewModel

---

## 7) Conclusão

A base de testes do app já cobre:

- regras de negócio centrais
- persistência e delegação de repositórios
- estados e eventos dos principais `ViewModels`
- comportamento visual e interativo das telas mais importantes

Se quiser, o próximo passo natural é adicionar testes para `AuthViewModel` e ampliar a cobertura de casos de borda nos filtros de receita.



