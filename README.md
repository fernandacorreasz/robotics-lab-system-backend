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
**Instruções para Construir e Rodar a Aplicação**

**Pré-requisitos**

- Docker
- Docker Compose

---

### Como Construir e Rodar

1. **Clone o repositório**:
    ```sh
    git clone https://github.com/fernandacorreasz/robotics-lab-system-backend.git
    cd robotics-lab-system-backend
    ```

---


 2.  **Construa e inicie os contêineres**:
    ```sh
    docker-compose up --build
    ```

3. **Verifique os logs para assegurar que tudo está funcionando corretamente**:
    ```sh
    docker-compose logs -f
    ```
---

### Serviços e Portas

- **Aplicação**: http://localhost:8080
- **PostgreSQL (Produção)**: localhost:5432
- **PostgreSQL (Teste)**: localhost:5433

---

### Acesso ao Swagger

4. Após iniciar os contêineres, você pode acessar a documentação da API através do Swagger:
    ```
    http://localhost:8080/swagger-ui/index.html#/
    ```

---
