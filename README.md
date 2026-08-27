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

- **Arquitetura em Camadas (Monorepo):** Separação clara entre `/backend` e `/frontend`.
- **Princípios SOLID:** Baixo acoplamento e inversão de dependência em serviços REST e componentes.
- **Validações de Regra de Negócio:**
  - Validador customizado de idade para Servidores (permitido apenas entre 18 e 75 anos).
  - Tratamento de exceções e integridade referencial ao excluir Secretarias vinculadas.
- **Diferenciais:** Exportação da listagem de Servidores para arquivo CSV via PrimeNG Table.

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

A API estará disponível em [http://localhost:8080].

O console do H2 pode ser acessado em [http://localhost:8080/h2-console] (JDBC URL: jdbc:h2:mem:servidoresdb, Usuário: sa, Senha em branco).

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

Acesse a aplicação no navegador em [http://localhost:4200].

✒️ Autor
Desenvolvido por Bruno Yoshioka.
