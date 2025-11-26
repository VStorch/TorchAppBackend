<img src = "docs/imgs/torch_logo.png" alt = "Logo Torch" width = "350">

---

# Backend 🐾

Backend desenvolvido em Java Spring Boot para o projeto Torch, responsável por fornecer APIs para gestão de usuários, pets, serviços, agendamentos e Pet Shops.<br>
O frontend está disponível [aqui.](https://github.com/VStorch/TorchApp)<br>
Já a página de redirecionamento para a redefinição de senha está [aqui.](https://github.com/VStorch/PaginaRedirecionamento)

---

### Índice

- [Descrição](#descrição)
- [Funcionalidades](#funcionalidades)
- [Tecnologias e Dependências](#tecnologias-e-dependências)
- [Arquitetura](#arquitetura)
- [Como rodar o projeto](#como-rodar-o-projeto)
- [Estrutura de pastas](#estrutura-de-pastas)
- [Modelos Principais](#modelos-principais)
- [Fluxo da API](#fluxo-da-api)
- [Integração com o frontend](#integração-com-o-frontend)
- [Desenvolvedores](#desenvolvedores)
- [Status do projeto](#status-do-projeto)

---

### Descrição

O Torch é um aplicativo móvel desenvolvido para facilitar o agendamento de horários em Pet Shops. Com uma interface intuitiva, o app permite que os usuários agendem rapidamente serviços para seus animais de estimação, enquanto os Pet Shops podem gerenciar seus horários de forma eficiente. O objetivo é proporcionar agilidade tanto para os clientes quanto para os fornecedores de serviços.

---

### Funcionalidades

#### Tipos de Usuário

O Torch possui dois perfis de uso, cada um com funcionalidades específicas:

1. Cliente
- Criação de conta e login
- Cadastro de pets
- Consulta de petshops, serviços e horários
- Visualização de histórico e agendamentos futuros
- Atualização de perfil
- Recuperação e redefinição de senha via token

2. Dono de PetShop
- Cadastro do seu Pet Shop
- Registro e edição de serviços
- Geração de horários disponíveis (slots)
- Acompanhamento de agendamentos
- Atualização de informações do Pet Shop
- Gerenciamento do próprio perfil

---

### Tecnologias e Dependências

**Base**
- Java 21
- Spring Boot 3.5.4
- Maven

**Módulos Spring utilizados**
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-thymeleaf
- spring-boot-starter-mail
- spring-boot-starter-validation
- spring-boot-starter-security

**Bando de dados**
- MySQL + Connector/J (runtime)

**Auxiliares**
- Lombok 1.18.32
- IntelliJ Annotations 24.0.1
- Spring Boot DevTools

**Testes**
- spring-boot-starter-test

---

### Arquitetura

---

### Como rodar o projeto

**Criar o banco de dados:**

```bash

create database petshop character set utf8mb4 collate utf8mb4_unicode_ci;

```

Para ele ser reconhecido pelo Spring Boot: Configure o aplication.properties com os dados do seu servidor MySQL.

---

### Estrutura de pastas

---

### Modelos Principais

---

### Fluxo da API

---

### Integração com o frontend

---

### Desenvolvedores

- [João Pedro Pitz](https://github.com/joaopedropitzz)
- [Leonardo Cortelim dos Santos](https://github.com/LeonardoCortelim)
- [Vinícius Storch](https://github.com/VStorch)

---

### Status do projeto

Em desenvolvimento.