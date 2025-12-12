# 🌍 Conversor de Moedas --- Java + API ExchangeRate

Um projeto simples e didático que realiza conversão de moedas
em tempo real utilizando a **ExchangeRate API**.\
Feito para treinar requisições HTTP e estruturas básica de aplicações em Java.

------------------------------------------------------------------------

## ✨ Funcionalidades

-   Conversão de moedas em tempo real 💱\
-   Usuário escolhe a moeda base e a moeda destino\
-   Usuário digita o valor que deseja converter\
-   Requisição HTTP usando `HttpClient`\
-   Tratamento de JSON usando **Gson**\
-   Menu interativo no console\
-   Estrutura limpa com classes separadas

------------------------------------------------------------------------

## 🧠 Como o projeto funciona (explicação didática)

### 1. **Usuário escolhe as moedas**

Um menu exibe opções como USD → BRL, BRL → EUR, e por aí vai.

Essas opções vêm do `enum OpcaoMenu`.

------------------------------------------------------------------------

### 2. **O programa atualiza as moedas**

A classe `ConectaAPI` recebe:

-   moeda base\
-   moeda alvo

------------------------------------------------------------------------

### 3. **O usuário informa o valor que quer converter**

O programa pergunta:

    Digite o valor que deseja converter:

E esse valor é enviado para o cálculo.

------------------------------------------------------------------------

### 4. **O programa chama a API**

No método `consultarCotacao()`:

-   Monta a URL\
-   Envia a requisição\
-   Recebe os dados JSON\
-   Retorna o corpo da resposta

------------------------------------------------------------------------

### 5. **O JSON é processado**

Com o método `resultJson(resposta, valor)`:

-   A taxa da moeda é lida do JSON\
-   O valor informado é multiplicado pela taxa\
-   O resultado é mostrado no console

------------------------------------------------------------------------

## 🚀 Tecnologias utilizadas

-   **Java 17+**\
-   **Gson** (para leitura do JSON)\
-   **HttpClient** (requisições HTTP)\
-   **ExchangeRate API**\
-   **Scanner** (entrada de dados)

------------------------------------------------------------------------

## 📦 Estrutura do Projeto

    src/
     ├── ConectaAPI.java      // Comunicação com API e processamento do JSON
     ├── Principal.java       // Menu e fluxo principal
     └── OpcaoMenu.java       // Enum com opções do conversor

------------------------------------------------------------------------

## 🧪 Exemplo de fluxo no terminal

    ===== MENU CONVERSOR DE MOEDAS =====
    1 - USD para BRL
    2 - BRL para USD
    3 - EUR para USD
    0 - SAIR
    Escolha uma opção:

    >> USD selecionado
    Digite o valor que deseja converter:
    100

    1 USD = 5.3270 BRL
    100.00 USD = 532.70 BRL

------------------------------------------------------------------------

## 🔑 Sobre a API

Este projeto usa a API:

    https://www.exchangerate-api.com/

------------------------------------------------------------------------

## 🧑‍💻 Autor

**Silas Tavares**\
Dev em evolução contínua