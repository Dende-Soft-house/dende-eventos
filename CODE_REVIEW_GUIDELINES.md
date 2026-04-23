# 🧭 Guia de Code Review: Kotlin + Android

Um bom code review não é sobre "caçar erro pequeno", é sobre garantir **qualidade, clareza e evolução do código**. Este guia reúne as melhores práticas dos nossos manuais para revisões eficientes no ecossistema Android.

---

## 1. Contexto e Comunicação
Antes de olhar linha por linha, entenda o propósito da mudança.

- **Entenda o contexto:** Qual problema essa mudança resolve? Existe um ticket ou User Story associada?
- **Seja gentil e construtivo:** O objetivo é melhorar o código, não criticar o autor.
- **Explique o "porquê":** Em vez de apenas dizer "mude isso", explique o benefício técnico ou de negócio.
- **Feedback de qualidade:** Evite comentários vagos como "melhorar isso". Prefira: *"Esse `!!` pode causar NPE. Sugiro usar `?.` com valor default."*
- **Elogie:** Se encontrar uma solução elegante, reconheça o bom trabalho.

## 2. Legibilidade e Naming (Prioridade Máxima)
Kotlin permite escrever código muito compacto — isso é bom, até deixar de ser legível.

- **Nomes claros:** Variáveis e funções devem descrever sua intenção. Evite abreviações genéricas.
- **Evite "Clever Code":** Código muito complexo para parecer "esperto" geralmente é difícil de manter.
- **Dá para entender rápido?** Se você precisar ler três vezes para entender a lógica, o código pode ser simplificado.

### ❌ Nomes Genéricos e Abreviações
```kotlin
val d = 30
val list = getItems()
var flag = true

val r = list.filter { it.a > 10 }.map { it.b }
```

### ✅ Nomes Descritivos e Claros
```kotlin
val daysUntilEvent = 30
val guestList = getGuestList()
var isInviteAccepted = true

val approvedUsers = users
    .filter { it.age > 10 }
    .map { it.name }
```

## 3. Kotlin Idiomático e Null Safety
- **Null Safety:** Evite o uso de `!!`. Prefira `?.`, `?:` (Elvis operator) ou encadeamento seguro.
- **Imutabilidade:** Prefira `val` a `var` e use coleções imutáveis (`listOf`, `mapOf`).
- **Data & Sealed Classes:** Use `data class` para modelos e `sealed class/interface` para representar estados (ex: UI States).
- **Controle de Fluxo:** Prefira `when` ao invés de `if` encadeado ou aninhado.

### ❌ Ifs Aninhados
```kotlin
if (status == "PENDING") {
    showPending()
} else if (status == "ACCEPTED") {
    showAccepted()
} else {
    showError()
}
```

### ✅ When (Legível e Exaustivo)
```kotlin
when (uiState) {
    is UiState.Pending -> showPending()
    is UiState.Accepted -> showAccepted()
    is UiState.Error -> showError()
}
```

- **Scope Functions:** Verifique se `apply`, `let`, `run`, `with` e `also` estão sendo usados corretamente e evite aninhamentos excessivos (overkill).
  - ❌ `user?.let { it.address?.let { it.city?.let { ... } } }`
  - ✅ `val city = user?.address?.city`

## 4. Android, Coroutines e Compose
- **Lifecycle:** Verifique se há vazamentos de memória (ex: referências de `Context` em Singletons ou processos longos).
- **Coroutines:** 
    - O código está no `Dispatcher` correto? (UI no `Main`, IO em `IO`).
    - O `Scope` está sendo cancelado corretamente com o ciclo de vida?
- **Jetpack Compose:**
    - **Recomposition:** Evite cálculos pesados diretamente no corpo da função `@Composable`. Use `remember`.
    - **State Hoisting:** O estado está sendo elevado adequadamente para tornar o componente testável e reutilizável?
    - **Stable Types:** Verifique se classes complexas passadas como parâmetro são `@Stable`.

## 5. Arquitetura e Responsabilidade
- **Respeito às camadas:** O código respeita a separação entre UI, Domain e Data da Clean Architecture?
- **Responsabilidade Única:** Evite métodos gigantes. Funções com 200 linhas ou mais são um sinal claro de que a responsabilidade deve ser dividida.

### ❌ Função "Deus" (Complexa e Difícil de Testar)
```kotlin
fun handleCheckout() {
    // 50 linhas validando usuário e sessão
    // 50 linhas calculando preços e descontos
    // 50 linhas processando pagamento
    // 50 linhas atualizando banco local e UI
}
```

### ✅ Funções Pequenas e Especializadas
```kotlin
fun handleCheckout() {
    val user = validateUser() ?: return
    val total = calculateTotal(user)
    processPayment(total)
    updateLocalData()
    updateUi()
}
```

## 6. Testes (Obrigatório Revisar)
- **Existência e Cobertura:** Existe teste para a nova lógica? Cobre casos felizes e de erro?
- **Legibilidade:** Os nomes dos testes são claros e descrevem o comportamento esperado?

## 7. Performance e Padrões
- **Performance (sem paranoia):** Evite loops desnecessários ou operações caras dentro de loops (ex: recomposição excessiva).
- **Consistência:** O código segue o estilo do projeto? Consistência > preferência pessoal.

---

## ✅ Checklist Rápido para o Revisor
- [ ] O código funciona conforme os requisitos?
- [ ] O código é legível e os nomes fazem sentido?
- [ ] Sem uso perigoso de `!!` (Null Safety garantida)?
- [ ] A lógica de negócio está correta e testada?
- [ ] Respeita a arquitetura e os padrões do projeto?
- [ ] Sem código comentado ou "logs" de debug esquecidos?
- [ ] As Coroutines e o estado da UI (Compose) estão bem gerenciados?
