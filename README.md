# TCF5 - Health Record Provider (Prontuário Unificado)

![Java](https://img.shields.io/badge/Java-21-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg) ![Keycloak](https://img.shields.io/badge/Keycloak-Secured-blueviolet.svg)

## 📖 Sobre o Projeto
Este microserviço faz parte do desafio do **Hackathon da Pós-Graduação em Arquitetura e Desenvolvimento Java (FIAP)**. O **Provider** atua como a fonte única da verdade para o histórico clínico do paciente no contexto do SUS.

Ele centraliza os atendimentos, exames e observações clínicas, permitindo buscas otimizadas pelo **CPF** ou **CNS (Cartão Nacional de Saúde)** e garantindo a interoperabilidade e a fácil leitura dos dados pelos profissionais de saúde.

## Principais Funcionalidades
* **Busca Centralizada:** Recuperação de todo o histórico de um paciente via CPF.
* **Filtros Inteligentes:** Capacidade de filtrar o histórico por `tipoRegistro` (ex: CONSULTA, EXAME) ou `especialidade`.
* **Evolução Clínica (PATCH):** Atualização de observações médicas mantendo a integridade do registro.
* **Tratamento Global de Erros:** Respostas padronizadas (RFC 7807) para validações (400 Bad Request) e recursos não encontrados (404 Not Found), garantindo uma excelente experiência de integração (Developer Experience).
* **Segurança Integrada:** Proteção de endpoints utilizando tokens JWT validados via **Keycloak**.

---

## Tecnologias Utilizadas
* **Java 21**
* **Spring Boot 4.x** (Web, Data JPA, Validation)
* **Spring Security & OAuth2 Resource Server**
* **PostgreSQL** (Banco de dados relacional)
* **Springdoc OpenAPI / Swagger** (Documentação)
* **Lombok** (Redução de boilerplate)

---

## Atenção: Configuração de Segurança (Keycloak)

Por padrão, esta aplicação está configurada para ambiente de **Produção/Avaliação**, exigindo um token JWT válido emitido por um servidor Keycloak rodando na porta `8081` (configurado no `application.properties`).

## Como testar localmente sem o Keycloak (Bypass)
Se você for um avaliador ou desenvolvedor e desejar testar os endpoints localmente sem subir a infraestrutura do Keycloak, siga estes passos:

1. Acesse o arquivo `src/main/java/com/tcf5/health/record/provider/config/SecurityConfig.java`.
2. Substitua o conteúdo da classe pelo código abaixo para permitir tráfego irrestrito:

```java
package com.tcf5.health.record.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
```
3. Reinicie a aplicação. Todos os endpoints estarão abertos.

## Como usar a API (Endpoints)

A documentação interativa completa (Swagger UI) fica disponível em:

👉 http://localhost:8080/swagger-ui.html

**Principais Rotas:**
* GET /api/v1/prontuarios/paciente/{cpf}: Lista o histórico completo.

* GET /api/v1/prontuarios/paciente/{cpf}/filtro: Lista com filtros opcionais (?tipo= ou ?especialidade=).

* GET /api/v1/prontuarios/{id}: Busca um registro específico pelo UUID interno.

## Observação Importante sobre o método PATCH

Para adicionar ou editar uma nota clínica, utilizamos o endpoint:
PATCH /api/v1/prontuarios/{id}/observacoes

Como testar no Postman:
Diferente de requisições tradicionais que recebem objetos JSON, este endpoint foi desenhado para receber o texto puro da observação para facilitar a inserção de parágrafos e quebras de linha.

No Postman, selecione o método PATCH.

Vá na aba Body, selecione a opção raw.

No dropdown ao lado de raw, mude de JSON para Text (o cabeçalho Content-Type deve ser text/plain).

Escreva a observação diretamente na caixa de texto:
Paciente relata melhora nas dores após medicação.

## Como rodar o projeto
1. Certifique-se de ter um banco PostgreSQL rodando (na porta padrão 5432) com um database chamado db_prontuarios.

2. Credenciais padrão configuradas no application.properties: user: admin | pass: admin

3. Clone o repositório.

Na raiz do projeto, execute o comando Maven:
```Bash
./mvnw spring-boot:run
```
4. O Hibernate (via ddl-auto=update) criará a tabela tb_prontuarios automaticamente.

## Decisões Arquiteturais (Pitch)
* Abandono do JSON HL7 Aninhado: Para adequar o MVP à realidade dos profissionais do SUS, substituímos
  campos aninhados complexos por um campo de texto rico (observacoes), focando na leitura humana e na evolução clínica direta.

* Validações na Borda (Fail-Fast): Uso de anotações @Size e @NotBlank garantem que requisições com CPFs
  inválidos ou observações vazias nem cheguem a sobrecarregar o banco de dados (retornando erro 400).

* Tratamento de Exceções Global: O @RestControllerAdvice impede o vazamento de stack traces Java para
  o front-end, retornando erros mapeados e estruturados que facilitam a vida de quem consome a API.
