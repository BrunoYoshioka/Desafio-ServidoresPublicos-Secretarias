# 🏢 Desenvolve Cidade - Gestão de Servidores Públicos e Secretarias

Aplicação Fullstack desenvolvida como desafio técnico para a gestão e vinculação de Servidores Públicos a Secretarias Municipais.

## 🚀 Tecnologias Utilizadas

### **Backend**

- **Java 21**
- **Spring Boot 3.x** (Spring Data JPA, Spring Validation, Spring Web)
- **H2 Database** (Banco de dados em memória)
- **Maven**

### **Frontend**

- **Angular 19**
- **PrimeNG 19** & **PrimeIcons**
- **Reactive Forms**
- **TypeScript & SCSS**

---

## 🛠️ Arquitetura e Padrões Aplicados

- **Arquitetura em Camadas:** Separação clara entre `/backend` (REST API) e `/frontend` (Single Page Application).
- **Padrão DTO (Data Transfer Object):** Utilização de `ServidorRequestDTO` e `SecretariaRequestDTO` desacoplando a camada de apresentação da camada de persistência.
- **Princípios SOLID & Clean Architecture:** Inversão de dependência através de interfaces de serviço (`ServidorService` e `SecretariaService`).
- **Tratamento Global de Exceções:** Centralizado via `@RestControllerAdvice` (`GlobalExceptionHandler`), garantindo retornos HTTP semânticos (`400 Bad Request`, `404 Not Found`, `409 Conflict`).
- **Defesa em Profundidade (Validações de Regra de Negócio):**
  - **Validação de Idade:** Permitida a idade do Servidor apenas entre 18 e 75 anos.
  - **Unicidade de E-mail (Servidores):** Garantida no Backend via Service e restrição `@Column(unique = true)` para permitir múltiplos vínculos com o mesmo nome.
  - **Unicidade de Nome e Sigla (Secretarias):** Trava defensiva preventiva no Frontend (UX instantânea) e no Backend (`existsBy...` no Service/Repository) prevenindo registros duplicados.
- **Diferenciais:**
  - Exportação da listagem de Servidores Públicos para arquivo CSV.
  - Interface responsiva com feedback visual dinâmico em formulários (tratamento do estado `touched` e destaque de campos inválidos).

---

## ⚙️ Como Executar o Projeto

### **1. Backend (Spring Boot)**

1. Certifique-se de ter o Java 21 e o Maven instalados.
2. Navegue até a pasta do backend:

```bash
cd backend
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

O console do H2 pode ser acessado em `http://localhost:8080/h2-console` (JDBC URL: jdbc:h2:mem:servidoresdb, Usuário: sa, Senha em branco).

### **2. Frontend (Angular)**

Certifique-se de ter o Node.js (v20+) instalado.

Navegue até a pasta do frontend:

```bash
cd frontend
```

Instale as dependências:

```bash
npm install
```

Execute o servidor de desenvolvimento:

```bash
ng serve
```

Acesse a aplicação no navegador em `http://localhost:4200`.

Para mais detalhes sobre o desenvolvimento, arquitetura, configurações e instalação sobre a aplicação frontend angular, acesse a [Documentação Frontend](https://github.com/BrunoYoshioka/Desafio-ServidoresPublicos-Secretarias/blob/main/frontend/README.md)

✒️ Autor
Desenvolvido por Bruno Yoshioka.
