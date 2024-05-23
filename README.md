# Desafio Técnico - Sistema de Gerenciamento de Tarefas Simplificado

Web services com Spring Boot e JPA/Hibernate utilizando H2 Database Engine, o projeto possui cobertura de testes feitos utilizando JUnit e Mockito. Aplicação back-end que permite aos usuários gerenciar tarefas diárias de forma simples.

## Aplicação

Requisitos Funcionais:
<ul>
  <li>Criação de Tarefa: O usuário pode adicionar uma nova tarefa. Cada tarefa deve ter um título, descrição, data de criação e um status (por exemplo, pendente, concluída).</li>
  <li>Listagem de Tarefas: O usuário pode ver todas as suas tarefas em uma lista, ordenadas pela data de criação.</li>
  <li>Edição de Tarefa: O usuário pode editar o título e a descrição de uma tarefa existente. Não é permitido editar a data de criação.</li>
  <li>Exclusão de Tarefa: O usuário pode excluir uma tarefa.</li>
  <li>Alteração de Status: O usuário pode alterar o status de uma tarefa para "concluída" ou voltar para "pendente".</li>
</ul>

Regras de Negócio:
<ul>
  <li>Validação de Dados: As tarefas devem ter um título e uma descrição. O título deve ter pelo menos 5 caracteres.</li>
  <li>Ordenação de Tarefas: Ao listar as tarefas, estas devem estar ordenadas pela data de criação, das mais antigas para as mais recentes.</li>
  <li>Limitação de Criação: Um usuário não pode criar mais de 10 tarefas pendentes ao mesmo tempo.</li>
</ul>

Requisitos Não Funcionais:
<ul>
  <li>Persistência: As tarefas devem ser salvas em um banco de dados (H2, MySQL, PostgreSQL, etc.).</li>
  <li>API REST: A comunicação entre o frontend e o backend deve ser feita através de uma API REST.</li>
  <li>Testes: Cobrir funcionalidades críticas com testes unitários e de integração.</li>
</ul>

## Como rodar

Para iniciar a aplicação basta clonar o repositório e rodar o arquivo main, o database abrirá em `http://localhost:8080/h2-console/` e está configurado para não ter senha, após isso é possível interagir com os endpoints via Postman ou Insomnia.

## Documentação no Postman
https://documenter.getpostman.com/view/21822407/2sA3QpBDMW
