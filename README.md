# robotics-lab-system-backend
[em desenvolvimento]
Essa aplicação  é um sistema de  controle  desenvolvido de forma didática para praticas do desenvolvimento de software e como entrega de portfólio acadêmico.  Este sistema foi projetado com o propósito de simplificar o controle de equipamentos, atividades, gerenciamento  de um laboratório de robótica, está sendo desenvolvido em Java utilizando Spring Boot. A aplicação, o banco de dados e outros serviços necessários estão orquestrados utilizando Docker Compose.

**Tecnologias Utilizadas:**

- **Java**
- **Spring Boot**
- **Docker**
- **Docker Compose**
- **PostgreSQL**
- **Flyway**
- **Spring Security e JWT**
- **Swagger**

---
A aplicação está configurada para utilizar Spring Security com JWT para proteger os endpoints e controlar o acesso baseado em permissões.

- **Autenticação e Autorização**: O projeto utiliza Spring Security e JWT para autenticação e autorização. Usuários podem se registrar e fazer login para receber um token JWT, que deve ser usado para acessar endpoints protegidos.
- **Migrações de Banco de Dados**: Utilizamos Flyway para gerenciar as migrações de banco de dados.
- **Documentação da API**: A documentação da API é gerada automaticamente utilizando Swagger para visualizar e testar os endpoints da API diretamente no navegador.
- **Testes**: Os testes unitários foram implementados utilizando JUnit e Mockito.

---

## Repositórios

- **Frontend:** [robotics-lab-system-frontend-Two](https://github.com/fernandacorreasz/robotics-lab-system-frontend-Two)
- **Backend:** [robotics-lab-system-backend](https://github.com/fernandacorreasz/robotics-lab-system-backend)

## Acesso à aplicação

- **Frontend em execução:** [http://4.196.97.77:5173/](http://4.196.97.77:5173/)
- **Documentação da API:** [http://4.196.97.77:9002/swagger-ui/index.html#/](http://4.196.97.77:9002/swagger-ui/index.html#/)

---

## Tecnologias Utilizadas

### Backend
- **Linguagem:** Java
- **Framework:** Spring Boot
- **Autenticação:** Spring Security com JWT
- **Gerenciamento de Banco de Dados:** Flyway e PostgreSQL
- **Containerização:** Docker e Docker Compose
- **Documentação:** Swagger
- **Testes:** JUnit e Mockito

### Frontend
- **Linguagem:** TypeScript
- **Framework:** React
- **Gerenciamento de Pacotes:** npm
- **Ambiente de Desenvolvimento:** Node.js
- **Estado atual:** Sem testes implementados


## Vídeo Explicativo

Um vídeo demonstrativo está sendo preparado para explicar as funcionalidades da aplicação. Assim que finalizado, será disponibilizado neste repositório.

