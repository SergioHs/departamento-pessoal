---
---
Projeto Spring Boot existente. Inspeciona o código antes de propor alterações.

## Objetivo

Adicionar ao endpoint de worker um filtro opcional por nome e paginação.

## Requisitos

- O filtro por nome deve ser opcional e case-insensitive.
- Implementar paginação com valores padrão e limites seguros, seguindo os padrões existentes.
- Preservar o contrato atual sempre que possível.
- Não adicionar dependências nem refatorar áreas não relacionadas.
- Adicionar testes para o filtro, ausência de resultados e paginação.

## Primeiro passo

Inspeciona o código relevante e apresenta um plano, os ficheiros que prevê afetar e quaisquer dúvidas. Não alteres código nesta primeira etapa; aguarda aprovação antes de implementar.

## Critérios de sucesso

- O endpoint aceita filtro opcional por nome e paginação com defaults e limites seguros.
- O filtro não diferencia maiúsculas de minúsculas.
- Os testes cobrem filtro, ausência de resultados e paginação.
- As alterações seguem os padrões do projeto e limitam-se ao necessário.
