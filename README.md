# Todo App

Aplicação full stack para gerenciamento de tarefas, desenvolvida com Spring Boot no back-end e JavaScript modular no front-end. O projeto implementa uma API REST com persistência em MySQL, validações de entrada, tratamento centralizado de erros e uma interface web para cadastro, edição, listagem, exclusão e filtragem de tarefas.

## Destaques do projeto

- API REST para CRUD completo de tarefas.
- Persistência com Spring Data JPA e MySQL.
- Validação de dados com Jakarta Bean Validation.
- Tratamento global de exceções com respostas padronizadas.
- Uso de DTOs para separar entrada e saída da API.
- Filtros por título, responsável, status e prioridade.
- Front-end em HTML, CSS e JavaScript modular.
- Servidor Node.js nativo para servir arquivos estáticos e atuar como proxy para a API.
- Documentação da API disponível via Swagger/OpenAPI.

## Tecnologias utilizadas

### Back-end

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Jakarta Validation
- MySQL
- Maven Wrapper
- Springdoc OpenAPI / Swagger UI

### Front-end

- HTML5
- CSS3
- JavaScript ES Modules
- Fetch API
- Node.js HTTP Server

## Estrutura do projeto

```text
todo-app/
├── todo-app-backend/      # API REST com Spring Boot
├── todo-app-frontend/     # Interface web e servidor Node.js
├── .gitignore
└── README.md
```

## Funcionalidades

- Criar uma nova tarefa.
- Listar todas as tarefas cadastradas.
- Buscar tarefa por ID.
- Atualizar uma tarefa existente.
- Excluir uma tarefa individual.
- Excluir todas as tarefas.
- Filtrar tarefas por título.
- Filtrar tarefas por responsável.
- Filtrar tarefas por status.
- Filtrar tarefas por prioridade.
- Categorizar tarefas.
- Definir prioridade, prazo, responsável e descrição.
- Registrar data de criação e data de atualização.

## Modelo de tarefa

Cada tarefa possui os seguintes campos principais:

| Campo | Descrição |
| --- | --- |
| `id` | Identificador único da tarefa |
| `titulo` | Título obrigatório da tarefa |
| `descricao` | Descrição opcional, limitada a 1000 caracteres |
| `status` | Situação atual da tarefa |
| `prioridade` | Nível de prioridade |
| `categoria` | Categoria da tarefa |
| `prazo` | Data limite para conclusão |
| `responsavel` | Pessoa responsável pela tarefa |
| `dataCriacao` | Data e hora de criação |
| `dataAtualizacao` | Data e hora da última atualização |

## Valores aceitos

### Status

- `PENDENTE`
- `CANCELADA`
- `EM_ANDAMENTO`
- `CONCLUIDA`

### Prioridades

- `BAIXA`
- `MEDIA`
- `ALTA`
- `MUITO_ALTA`

### Categorias

- `TRABALHO`
- `ESTUDOS`
- `PESSOAL`
- `SAUDE`
- `FINANCAS`
- `LAZER`
- `COMPRAS`
- `OUTROS`

## Pré-requisitos

Antes de iniciar, é necessário ter instalado:

- Java 21
- Node.js
- MySQL
- Git

O projeto utiliza Maven Wrapper, então não é obrigatório ter o Maven instalado globalmente.

## Configuração do banco de dados

Crie um banco MySQL chamado `tarefadb`:

```sql
CREATE DATABASE tarefadb;
```

O repositório inclui um arquivo seguro de exemplo em `todo-app-backend/src/main/resources/application-example.properties`. Copie esse arquivo para `application.properties` no mesmo diretório e configure suas variáveis de ambiente:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/tarefadb}
spring.datasource.username=${DB_USERNAME:}
spring.datasource.password=${DB_PASSWORD:}
spring.jpa.hibernate.ddl-auto=${JPA_DDL_AUTO:update}
```

No Windows PowerShell:

```powershell
Copy-Item src/main/resources/application-example.properties src/main/resources/application.properties
```

No Linux/macOS:

```bash
cp src/main/resources/application-example.properties src/main/resources/application.properties
```

Antes de iniciar a API, configure as variáveis conforme seu ambiente.

No Windows PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/tarefadb"
$env:DB_USERNAME="seu_usuario"
$env:DB_PASSWORD="sua_senha"
```

No Linux/macOS:

```bash
export DB_URL="jdbc:mysql://localhost:3306/tarefadb"
export DB_USERNAME="seu_usuario"
export DB_PASSWORD="sua_senha"
```

## Como iniciar o back-end

Acesse a pasta do back-end:

```bash
cd todo-app-backend
```

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

Por padrão, a API ficará disponível em:

```text
http://localhost:8080
```

## Como iniciar o front-end

Com o back-end em execução, abra outro terminal e acesse a pasta do front-end:

```bash
cd todo-app-frontend
```

Execute:

```bash
npm run dev
```

Ou diretamente:

```bash
node server.js
```

A interface ficará disponível em:

```text
http://localhost:5173
```

O servidor do front-end redireciona chamadas de `/api` para `http://localhost:8080`, facilitando a comunicação com a API durante o desenvolvimento.

Caso precise alterar a porta do front-end ou o endereço do back-end, use:

```powershell
$env:PORT="5173"
$env:BACKEND_URL="http://localhost:8080"
npm run dev
```

## Documentação da API

Com o back-end rodando, acesse:

```text
http://localhost:8080/swagger-ui.html
```

ou:

```text
http://localhost:8080/swagger-ui/index.html
```

## Endpoints principais

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/tarefas` | Lista todas as tarefas |
| `POST` | `/tarefas` | Cria uma nova tarefa |
| `GET` | `/tarefas/{id}` | Busca uma tarefa por ID |
| `PUT` | `/tarefas/{id}` | Atualiza uma tarefa |
| `DELETE` | `/tarefas/{id}` | Remove uma tarefa |
| `DELETE` | `/tarefas/todos` | Remove todas as tarefas |
| `GET` | `/tarefas/titulo/{titulo}` | Busca tarefas por título |
| `GET` | `/tarefas/responsavel/{responsavel}` | Busca tarefas por responsável |
| `GET` | `/tarefas/status/{status}` | Filtra tarefas por status |
| `GET` | `/tarefas/prioridade/{prioridade}` | Filtra tarefas por prioridade |

## Exemplo de payload

```json
{
  "titulo": "Estudar Spring Boot",
  "statusTarefa": "PENDENTE",
  "prioridade": "MEDIA",
  "categoria": "ESTUDOS",
  "descricao": "Revisar controllers, DTOs e services",
  "prazo": "2026-06-10",
  "responsavel": "Pedro"
}
```

## Validações implementadas

- `titulo` é obrigatório e deve ter entre 3 e 100 caracteres.
- `statusTarefa` é obrigatório.
- `prioridade` é obrigatória.
- `categoria` é obrigatória.
- `descricao` pode ter no máximo 1000 caracteres.
- `prazo` não pode ser uma data passada.
- `responsavel` é obrigatório.

## Tratamento de erros

A aplicação possui um `GlobalExceptionHandler` para centralizar respostas de erro, incluindo:

- Tarefa não encontrada.
- Regras de negócio inválidas.
- Erros de validação de campos.
- Parâmetros inválidos em filtros e rotas.

## Como executar os testes

Na pasta `todo-app-backend`, execute:

```bash
.\mvnw.cmd test
```

No Linux/macOS:

```bash
./mvnw test
```

## Sugestão de descrição para LinkedIn

Desenvolvi uma aplicação full stack de gerenciamento de tarefas com Java 21, Spring Boot, Spring Data JPA, MySQL e JavaScript modular. O projeto conta com API REST, CRUD completo, filtros por título, responsável, status e prioridade, validações com Jakarta Bean Validation, tratamento global de exceções, DTOs para organização do contrato da API e documentação via Swagger/OpenAPI.

No front-end, construí uma interface web responsiva utilizando HTML, CSS e JavaScript, consumindo a API com Fetch API e utilizando um servidor Node.js nativo como proxy local para simplificar o desenvolvimento.

Esse projeto reforçou conceitos importantes de arquitetura em camadas, integração front-end/back-end, persistência relacional, validação de dados, tratamento de erros e boas práticas na construção de APIs REST.

## Próximas melhorias

- Adicionar autenticação e autorização.
- Criar paginação e ordenação nas listagens.
- Implementar testes unitários e de integração mais completos.
- Adicionar Docker Compose para subir MySQL, back-end e front-end juntos.
- Criar pipeline de CI/CD.
- Melhorar observabilidade com logs estruturados.
