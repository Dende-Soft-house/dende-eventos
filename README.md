# Dendê Eventos

Dendê Eventos é um aplicativo Android desenvolvido para facilitar a gestão e participação em eventos. O projeto foi construído seguindo os princípios de **Clean Architecture** para garantir manutenibilidade, testabilidade e escalabilidade.

## 🚀 Tecnologias

- [Kotlin](https://kotlinlang.org/) - Linguagem de programação moderna.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Toolkit moderno para construção de UI nativa.
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) - Organização de código em camadas (Data, Domain, Presentation).
- [Material 3](https://m3.material.io/) - Sistema de design do Google.

## 🏗️ Arquitetura

O projeto está estruturado nas seguintes camadas:

- **Domain:** Contém as entidades de negócio e casos de uso (Use Cases). É a camada mais interna e independente de frameworks.
- **Data:** Implementa as interfaces de repositório definidas no Domain, lidando com fontes de dados locais ou remotas.
- **Core:** Contém utilitários, extensões e o **Design System** (componentes reutilizáveis como `DendeButton`, `InviteCard`, etc).
- **Presentation/UI:** Camada responsável por exibir os dados e lidar com a interação do usuário usando Jetpack Compose.

## 🛠️ Como executar

1. Clone o repositório.
2. Abra o projeto no **Android Studio (Ladybug ou superior)**.
3. Sincronize o Gradle.
4. Execute o app em um emulador ou dispositivo físico.

## 📁 Estrutura de Pastas

```text
br.com.dende.dendeeventos
├── core             # Design System e utilitários base
├── data             # Repositórios e Data Sources
├── domain           # Modelos de negócio e Use Cases
├── ui               # Telas, ViewModels e Temas (Presentation)
└── MainActivity.kt  # Ponto de entrada do app
```
