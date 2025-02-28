# Desafio PicPay - Gestão de Transações

## 📌 Sobre o Projeto
Este projeto é uma API desenvolvida em **Java 17** com **Spring Boot**, que permite a gestão de usuários (pessoas físicas e jurídicas) e a realização de transações financeiras entre eles. O sistema garante a consistência das operações, aplicando validações e enviando notificações por e-mail.

A aplicação utiliza **PostgreSQL via Docker Compose** e segue boas práticas, como:

- Uso de padrões de projeto (Chain of Responsibility para validações)
- Assincronismo (@Async) para envio de e-mails
- Criptografia de senhas com Spring Security
- HATEOAS para facilitar a navegação
- Documentação via Swagger

---

## 🏛 Estrutura do Sistema
O projeto possui três entidades principais:

### 🧑‍💼 Usuário (`Usuario`)
- Nome completo
- E-mail e senha (armazenada de forma segura com `PasswordEncoder`)
- CPF ou CNPJ
- Tipo (Usuario Comum ou Usuario Logista)
- Conta vinculada

### 💰 Conta (`Conta`)
- Saldo disponível
- Histórico de transações enviadas e recebidas
- Associação com um usuário

### 🔄 Transação (`Transacao`)
- Conta de origem e destino
- Valor da transação
- Instante da transação
- Status da transação (CONCLUIDA ou EMAIL PENDENTE)

---

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security** (para criptografia de senha)
- **HATEOAS** (para facilitar a navegação)
- **Swagger** (para documentação)
- **PostgreSQL via Docker Compose**
- **Maven**

---

## 📡 Principais Endpoints

### Usuário (`/usuarios`)
- `GET` → Listar usuários (com paginação)
- `GET /{id}` → Buscar usuário por ID
- `POST` → Criar novo usuário
- `PATCH/{id}` → Atualizar nome e/ou senha

### Transação (`/transacoes`)
- `POST` → Efetuar uma transferência
- `GET /enviadas/{userId}` → Listar transações enviadas pelo usuário
- `GET /recebidas/{userId}` → Listar transações recebidas pelo usuário

---

## 🔮 Melhorias Futuras
- Autenticação completa para permitir login/logout e controle de sessões
- Testes unitários e de integração para garantir a qualidade do código
- Implementação de um sistema de notificações mais robusto
