# Frontend

Este projeto foi gerado utilizando [Angular CLI](https://github.com/angular/angular-cli) versão 19.2.15.

## Arquitetura e Estrutura do Frontend

O frontend foi desenvolvido utilizando **Angular 19** com uma arquitetura modular focada em boas práticas de desacoplamento, reuso e manutenção (*Core Module Pattern*).

### Organização de Pastas

```text
src/
├── app/
│   ├── core/                  # Módulo central com recursos singleton
│   │   ├── models/            # Interfaces e contratos de dados (Secretaria, Servidor)
│   │   ├── services/          # Serviços HTTP desacoplados para integração REST
│   │   └── validators/        # Validadores customizados para formulários (ex: Idade)
│   ├── app.component.*        # Componente raiz da aplicação
│   └── app.config.ts          # Configurações globais e provedores Standalone
├── environments/              # Centralização de URLs da API (Dev Local e Produção)
│   ├── environment.ts         # Configuração para desenvolvimento local
│   └── environment.prod.ts    # Configuração de substituição para build de produção
```

## Servidor de desenvolvimento

Para iniciar um servidor de desenvolvimento local, execute:

```bash
ng serve
```

Assim que o servidor estiver em execução, abra o seu navegador e acesse `http://localhost:4200/`. A aplicação será recarregada automaticamente sempre que você modificar qualquer um dos arquivos de código-fonte.

## Code scaffolding

O Angular CLI inclui ferramentas poderosas de geração de estrutura de código (*scaffolding*). Para gerar um novo componente, execute:

```bash
ng generate component component-name
```

Para uma lista completa de schematics disponíveis (como `components`, `directives` ou `pipes`), execute:

```bash
ng generate --help
```

## Building

Para compilar o projeto, execute:

```bash
ng build
```

Isso compilará seu projeto e armazenará os artefatos de build no diretório `dist/`. Por padrão, o build de produção otimiza sua aplicação para desempenho e velocidade.

## Executando testes unitários

Para executar testes unitários com o executor de testes [Karma](https://karma-runner.github.io), utilize o seguinte comando:

```bash
ng test
```

## Executando testes de ponta a ponta

Para testes de ponta a ponta (e2e), execute:

```bash
ng e2e
```

O Angular CLI não vem com um framework de testes ponta a ponta por padrão. Você pode escolher um que atenda às suas necessidades.

## Recursos adicionais

Para mais informações sobre o uso do Angular CLI, incluindo referências detalhadas de comandos, visite a página [Visão Geral e Referência de Comandos do Angular CLI](https://angular.dev/tools/cli).
