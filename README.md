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

```

TorchAppBackend/
├── .mvn/
├── docs/
│   └── imgs/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── torchapp/
│                   └── demo/
│                       ├── config/
│                       ├── controllers/
│                       ├── dtos/
│                       ├── enums/
│                       ├── exceptions/
│                       ├── infra/
│                       ├── mappers/
│                       ├── models/
│                       ├── repositories/
│                       └── services/
│       └── resources/

```

#### Pastas principais

Abaixo está uma explicação sobre as principais pastas do projeto:

- ``config/``: Contém as classes de configuração da aplicação.
- ``controllers/``: Contém as classes responsáveis pelos endpoints REST da API, como os controladores para usuários, petshops, serviços, etc.
- ``dtos/``: Contém os objetos de transferência de dados (DTOs), usados para a comunicação entre as camadas da aplicação.
- ``enums/``: Contém as enumerações utilizadas na aplicação, no caso, os tipos de usuários e o status de agendamentos.
- ``exceptions/``: Contém as exceções personalizadas, usadas para tratamento de erros específicos da aplicação.
- ``infra/``: Contém classes relacionadas à infraestrutura e tratamento de exceções.
- ``mappers/``: Contém as classes de mapeamento de dados entre objetos, como entre DTOs e entidades JPA.
- ``models/``: Contém as entidades JPA, que representam as tabelas do banco de dados. Essas classes são mapeadas para o banco de dados com anotações do JPA.
- ``repositories/``: Contém as interfaces dos repositórios, responsáveis pela abstração do acesso ao banco de dados. Elas utilizam Spring Data JPA ou outros métodos para fornecer operações de CRUD (Create, Read, Update, Delete) e consultas personalizadas de forma automática.
- ``services/``: Contém a lógica de negócios da aplicação, como a manipulação de agendamentos, usuários e petshops.
- ``resources/``: Contém arquivos e pastas importantes utilizados na aplicação, como:
    - ``application.properties``: Arquivo responsável pela configuração do Spring Boot.
    - ``templates/``: Contém arquivos HTML usados para enviar e-mails com informações personalisadas.
    - ``images/``: Contém imagens utlizadas no envio de e-mail.

---

### Modelos Principais

Os modelos abaixo representam as entidades do domínio do Torch, estruturadas seguindo boas práticas do JPA e refletindo o funcionamento do fluxo de agendamentos, pets, serviços e pet shops.

- User<br>
    Representa o usuário do sistema (cliente ou dono de pet shop).

    **Principais atributos:**
    - ``id``
    - ``name``
    - ``surname``
    - ``phone``
    - ``email``
    - ``password``
    - ``role`` (enum: CLIENT, OWNER)
    - ``pets`` (lista de animais caso o usuário seja cliente)

- Pet

    **Principais atributos:**
    - ``id``
    - ``name``
    - ``species``
    - ``breed``
    - ``weight``
    - ``birthDate``
    - ``user`` (Dono do pet)

- PetShop<br>
    Representa um pet shop cadastrado por um usuário do tipo "owner".

    **Principais atributos**
    - ``id``
    - ``cep``, ``state``, ``state``, ``city``, ``neighborhood``, ``street``, ``number``, ``addressComplement``
    - ``phone``
    - ``email``
    - ``cnpj``
    - ``services``
    - ``evaluations``
    - ``appointments``
    - ``owner`` (Usuário dono do pet shop)

- PetShopInformation<br>
    Usado para armazenar informações complementares e configuráveis do Pet Shop, permitindo que o dono personalize seu perfil.

    **Principais atributos**
    - ``id``
    - ``name``
    - ``description``
    - ``logoUrl``
    - ``services``
    - ``instagram``
    - ``facebook``
    - ``website``
    - ``whatsapp``
    - ``comercialPhone``
    - ``comercialEmail``
    - ``userId``
    - ``createdAt``
    - ``updatedAt``

- PetShopServices<br>
    Serviços ofertados pelo pet shop.

    **Principais atributos**
    - ``id``
    - ``name``
    - ``price``
    - ``petShop``
    - ``appointments``
    - ``availableSlots``

- AvailableSlots
    Horários disponíveis que o pet shop abre no sistema para seus serviços.

    **Principais atributos**
    - ``id``
    - ``date``
    - ``startTime``
    - ``endTime``
    - ``booked``

- Schedule
    Cadastro do horário de abertura e fechamento do pet shop.

    **Principais atributos**
    - ``id``
    - ``petShopId``
    - ``dayOfWeek``
    - ``openTime``
    - ``closeTime``
    - ``isActive``

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