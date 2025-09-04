# Auto-Escola API — Checkpoint 1 (Cadastro & Listagem)

API REST em **Spring Boot 3** para gerenciar **Instrutores** e **Alunos** de uma auto-escola.  
Esta entrega cobre **cadastro** e **listagem** (com **paginação** e **ordenação por nome**).

> **Stack**: Java 21, Spring Boot (Web, Validation, Data JPA), Flyway, MySQL 8, Lombok, Maven.

---

## 🚀 Como executar

### Pré-requisitos
- **JDK 21+**
- **Maven 3.9+**
- **MySQL 8** (pode ser via Docker)

### Subir MySQL com Docker (porta 3306)
```bash
docker run --name mysql-autoescola   -e MYSQL_ROOT_PASSWORD=fiap   -e MYSQL_DATABASE=spring_boot_project   -p 3306:3306   -d mysql:8
```

### Configuração da aplicação (`src/main/resources/application.properties`)
> Se você usar outra porta, ajuste `3306` na URL.
```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/spring_boot_project?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=fiap

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

### Rodar
```bash
mvn clean package
mvn spring-boot:run
# ou
java -jar target/*SNAPSHOT.jar
```

---

## 🗃️ Migrações (Flyway)

Pasta: `src/main/resources/db/migration`

- `V1__create-table-instrutores.sql` – tabela de instrutores (modelo do professor)
- `V2__create-table-alunos.sql` – tabela de alunos (**CPF UNIQUE**)

> **Dica:** para evitar duplicidade por máscara (ex.: `111.222.333-44` vs `11122233344`), o código **normaliza CPF/CEP para só dígitos** antes de persistir.

---

## 🔗 Endpoints

Base URL: `http://localhost:8080`

### Instrutores
- **POST** `/instrutores` — cadastra instrutor
- **GET** `/instrutores` — lista paginada/ordenada por `nome`

**Exemplo – POST /instrutores**
```bash
curl -X POST http://localhost:8080/instrutores   -H "Content-Type: application/json"   -d '{
    "nome": "Maria Silva",
    "email": "maria@autoescola.com",
    "cnh": "12345678901",
    "especialidade": "CARROS",
    "endereco": {
      "logradouro":"Rua A","numero":"100","complemento":"",
      "bairro":"Centro","cidade":"São Paulo","uf":"SP","cep":"01000000"
    }
  }'
```

**Exemplo – GET /instrutores (paginado)**
```bash
curl "http://localhost:8080/instrutores?page=0&size=10"
```

---

### Alunos
- **POST** `/alunos` — cadastra aluno
- **GET** `/alunos` — lista paginada/ordenada por `nome`

**Exemplo – POST /alunos**
```bash
curl -X POST http://localhost:8080/alunos   -H "Content-Type: application/json"   -d '{
    "nome":"João da Silva",
    "email":"joao@exemplo.com",
    "telefone":"+55 11 98888-8888",
    "cpf":"11122233344",
    "endereco":{
      "logradouro":"Rua C","numero":"50","complemento":"",
      "bairro":"Centro","cidade":"São Paulo","uf":"SP","cep":"01000000"
    }
  }'
```

**Exemplo – GET /alunos (paginado)**
```bash
curl "http://localhost:8080/alunos?page=0&size=10"
```

---

## 📦 Payloads (DTOs)

### `Endereco` (reutilizado em Instrutor/Aluno)
```json
{
  "logradouro": "Rua X",
  "numero": "123",
  "complemento": "",
  "bairro": "Bairro Y",
  "cidade": "Cidade Z",
  "uf": "SP",
  "cep": "01000000"
}
```

> **Validações importantes**
> - **CEP**: normalize para **8 dígitos** (sem hífen)
> - **CPF**: 11 dígitos (**UNIQUE no banco**)
> - **E-mail**: formato válido

---

## 🔎 Paginação & Ordenação

- Query params padrão do Spring Data: `?page=0&size=10&sort=nome,asc`
- A API já define `@PageableDefault(size=10, sort="nome")`

---

## 🧩 Tecnologias

- **Spring Boot 3**, **Spring Web**, **Spring Validation**, **Spring Data JPA**
- **Flyway** (migrações)
- **MySQL 8**
- **Lombok** (getters/constructors), **Maven**

---

## 🧯 Troubleshooting

- **`Failed to configure a DataSource: 'url' attribute is not specified`**  
  Verifique se o `application.properties` está em `src/main/resources` e a URL está correta.

- **`Failed to determine a suitable driver class`**  
  Falta a dependência do MySQL no `pom.xml` ou o classpath da IDE não pegou. Garanta:
  ```xml
  <dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
  </dependency>
  ```

- **Conflito de porta 3306**  
  Se já existe MySQL local, suba o Docker em outra porta (ex.: `-p 3307:3306`) e troque a porta na URL.

- **CPF duplicado**  
  O banco tem **UNIQUE**; o código normaliza para **só dígitos**.
  Se necessário, limpe dados inválidos e rode novamente.

---

## 👥 Integrantes
- Enzo Luiz Goulart - RM99666
- Gustavo Henrique Santos Bonfim - RM98864
- Kayky Paschoal Ribeiro - RM99929
- Lucas Yuji Farias Umada - RM99757
- Natan Eguchi dos Santos - RM98720 

---
