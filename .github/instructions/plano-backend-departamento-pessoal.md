# Plano de implementação para IA — backend de Departamento Pessoal (Brasil)

## Instrução principal

Implemente em etapas, nesta ordem, no projeto Maven existente `com.experiment:departamento-pessoal`. Entregue código funcional e executável ao final de cada etapa. Comece por cadastros e fluxos básicos. Prepare portas para integrações externas e use adaptadores mock locais; implementar comunicação real com terceiros é TODO da última etapa. Não afirme conformidade trabalhista/fiscal antes da validação das regras com fontes oficiais e especialista de DP.

## Base técnica e decisões

- Java 21, Spring Boot 4.1.1, Maven, Spring MVC, Spring Data JPA, Spring Security e springdoc 3.1.0, conforme POM fornecido. Preserve as dependências já presentes; valide a resolução executando `./mvnw test` (ou `mvn test` se não houver wrapper).
- Adicione quando necessário: MySQL Connector/J (`com.mysql:mysql-connector-j`), Flyway e `org.flywaydb:flyway-mysql`, Bean Validation, suporte de geração/verificação JWT (Spring Security OAuth2 Resource Server + JOSE ou outra biblioteca mantida e justificada), e suporte geral de testes se os starters segmentados não trouxerem JUnit/AssertJ necessários. Não adicione Kafka, Redis ou Keycloak no MVP.
- Monólito modular por domínio: `auth`, `organization`, `employee`, `contract`, `attendance`, `vacation`, `absence`, `benefits`, `payroll`, `termination`, `integration`, `shared`. Em cada módulo, mantenha `api`, `application`, `domain` e `infrastructure` onde fizer sentido. Evite dependência direta de entidades JPA entre módulos quando um identificador e uma consulta de aplicação bastarem.
- Banco MySQL 8.x na porta 3306 com migrations Flyway; `ddl-auto=validate` fora de testes; valores em BRL com `BigDecimal`, escala e arredondamento definidos; datas com `LocalDate`, competência com `YearMonth` mapeada explicitamente, instantes com `Instant`/UTC. Nunca use `double` para dinheiro.
- Use `record` para requests, responses, comandos, resultados imutáveis e eventos; classes normais para entidades JPA e agregados mutáveis. Use `sealed interface` e pattern matching em `switch` quando modelarem variantes reais (por exemplo, tipos de ajuste); enums apenas para conjuntos fechados. Prefira coleções imutáveis nas respostas e `Optional` somente em retornos; não use recursos novos como enfeite.
- API `/api/v1`; paginação, filtros, ordenação controlada, DTOs separados das entidades; `ProblemDetail` para erros, validação de entrada, códigos HTTP corretos, OpenAPI e exemplos. IDs UUID gerados no servidor.
- Multiempresa desde o início: cada dado operacional pertence a `company_id`; toda consulta/mutação deve validar o escopo da empresa autenticada. Usuário administrador pode receber escopo explícito. Nunca confiar em `companyId` enviado pelo navegador para conceder acesso.

## Autenticação solicitada

JWT simples emitido pelo próprio backend, sem provedor externo. `POST /api/v1/auth/login` valida senha com hash forte; `GET /api/v1/auth/me` retorna usuário e permissões. Roles iniciais `ADMIN`, `DP`, `GESTOR`, `EMPREGADO`. API stateless, token Bearer com `sub`, `companyId`, roles, `iat`, `exp` e `iss`; assinatura com segredo forte em variável de ambiente (ou chave assimétrica configurada), nunca no Git. Configurar duração longa por propriedade, **padrão de desenvolvimento de 30 dias**, e expor configuração distinta para produção. Mesmo com duração longa, incluir `tokenVersion` no usuário e validá-lo para invalidar sessões após troca de senha/desativação; documentar que a invalidação exige consulta ao banco ou cache. Rate limit básico no login, nenhum dado sensível no JWT, CORS restrito à origem React e endpoints de administração protegidos. Rotas de recuperação de senha podem ficar como TODO do MVP.

## Sequência de implementação e critérios de aceite

### 0. Infraestrutura executável

- Usar MySQL 8.x em `localhost:3306` no desenvolvimento (Docker Compose opcional, publicando `3306:3306`), perfis `local` e `test`, migrations Flyway a partir de V1, health endpoint se incluir Actuator, tratamento central de erros, validação, OpenAPI e dados fictícios opcionais de desenvolvimento.
- Executar `criar-banco-departamento-pessoal.sql` uma vez com um usuário autorizado a criar bancos; criar tabelas, índices e dados apenas nas migrations `src/main/resources/db/migration/V*.sql`. O script cria somente o schema, sem `CREATE USER`, `GRANT` ou credenciais fixas.
- Exemplo `application-local.yml`: `spring.datasource.url: jdbc:mysql://localhost:3306/departamento_pessoal?useUnicode=true&characterEncoding=utf8`; para desenvolvimento local, `spring.datasource.username: root` e `spring.datasource.password: passwd`; `spring.jpa.hibernate.ddl-auto: validate`; `spring.flyway.enabled: true`. Essas credenciais são apenas para ambiente local; em produção, usar variáveis de ambiente para usuário e senha. Para app dentro do Compose, usar o nome do serviço MySQL como host, mantendo a porta 3306.
- No MySQL, mapear valores monetários para `DECIMAL(19,2)` (ou precisão adequada por campo), instantes com conversão UTC, booleanos e UUIDs de forma consistente, documentando a escolha `BINARY(16)` ou `CHAR(36)`; evitar tipo `YEAR` para competência e usar mês `YYYY-MM` com conversor ou dois inteiros com restrições.
- Critério: aplicação inicia num banco recém-criado e migrations rodam; não usar `root`/`passwd` fora do perfil local e não versionar credenciais de produção.

### 1. Usuários e empresas

- CRUD de empresa, estabelecimento, departamento, cargo e usuário; login JWT; permissões por role e empresa. Endpoints de listagem paginada e busca.
- Entidades com `createdAt`, `updatedAt`, `version` otimista; desativação em vez de apagar registros referenciados.
- Critério: usuário da empresa A não consulta nem altera registros da B; login, token inválido/expirado e autorização têm testes de integração.

### 2. Empregados e contratos

- CRUD de empregado, dependentes e dados de contato; cadastro de contrato, salário, cargo, lotação, jornada e status. Separar dados pessoais do vínculo: uma pessoa pode ter vínculos distintos.
- Criar históricos de salário/cargo/jornada com `validFrom` e `validTo`, proibir períodos conflitantes; não sobrescrever história. CPF com validação; dados sensíveis mascarados em listagens e logs.
- Fluxo de admissão interno: rascunho → validação → admitido; gravar evento interno `EmployeeHired`; saída governamental vai apenas para mock.
- Critério: cadastro e atualização funcionam; consulta por data devolve vínculo e remuneração válidos naquela data.

### 3. Ponto e ausências básicos

- CRUD de escala/jornada, marcações e justificativas; consolidar apontamento mensal com faltas, atrasos e horas extras simples. Registrar afastamentos com datas e status; bloquear sobreposição inconsistente.
- Começar com jornada diurna padrão e regras parametrizadas; turnos que cruzam meia-noite, banco de horas, acordos e adicional noturno são etapa posterior, sem cálculos improvisados.
- Critério: fechamento do apontamento produz resumo versionado consumível pela folha; reabertura exige motivo e auditoria.

### 4. Férias e benefícios básicos

- Períodos aquisitivos e solicitações de férias: solicitar → aprovar/rejeitar → registrar gozo; impedir sobreposição e saldo negativo. Cálculo financeiro fica para o motor de folha quando as regras forem definidas.
- CRUD de tipos de benefício, adesão e valores; lançamentos mensais para a folha. Criar dados mock de operadoras sem chamadas externas.
- Critério: aprovações registram ator/data, saldos reproduzíveis, e resultados entram na prévia da folha.

### 5. Folha inicial controlada

- Modelar competência, rubrica com incidências e vigência, lançamentos, demonstrativo e `calculationVersion`. Primeiro cálculo: salário-base proporcional conforme regra **explicitamente configurada e aprovada**, proventos e descontos manuais, benefícios e total líquido. Não embutir tabelas fictícias de INSS/IRRF/FGTS como se fossem oficiais.
- Fluxo: aberta → prévia → conferida → fechada. Fechamento cria snapshot imutável de entradas, regras, rubricas e saídas; correções por ajuste/reabertura auditada; processamento idempotente por empresa + competência + empregado + versão.
- Reservar interfaces `TaxRuleProvider` e `PayrollCalculationRule`, implementações mock sinalizadas como **SIMULAÇÃO — não usar para pagamento ou obrigações oficiais**. Separar cálculo de tributos reais como TODO validado.
- Critério: prévias reproduzíveis, não duplica rubricas, folha fechada não sofre edição direta, teste de regra e de arredondamento.

### 6. Rescisão e cálculos complexos

- Cadastro do desligamento, tipo, data, documentos e status; prévia de verbas por componentes. Férias, 13º, aviso e reflexos só são liberados para uso real após regras versionadas e cenários conferidos por especialista.
- Evoluir ponto para escalas, virada de dia, banco de horas; férias para fracionamento/médias; folha para retroatividade, diferenças e retificação interna. Preservar folha original e criar ajustes vinculados.
- Critério: alteração retroativa gera diferença identificável, nunca altera silenciosamente demonstrativo fechado.

### 7. Integrações simuladas e preparação de produção

- Definir portas no domínio/aplicação: `GovernmentEventGateway`, `BankPaymentGateway`, `TimeClockGateway`, `BenefitsGateway`, `OccupationalHealthGateway`, `AccountingGateway`, `DocumentStorageGateway`.
- Implementar adaptadores `mock` sob perfil `local/test`; retornam protocolo fictício, status configurável (aceito/rejeitado/temporariamente indisponível), latência e falha simuláveis. Registrar `IntegrationAttempt` com correlation ID, payload mínimo mascarado, status, tentativas e timestamps. Manter mapeadores em `integration`, longe das entidades de domínio.
- Criar outbox transacional para `EmployeeHired`, `TimesheetClosed`, `VacationApproved`, `PayrollClosed`, `EmployeeTerminated`; worker local publica para handlers mock com deduplicação e retentativa limitada. Kafka é evolução opcional, não requisito.
- Critério: falha no mock não desfaz folha fechada; tentativa pode ser reprocessada sem duplicação; tela consulta status e protocolos mock.

### 8. TODO final — integrações externas reais

- eSocial (eventos, layouts, certificados, sequenciamento, recibos e retificações); FGTS Digital e DCTFWeb conforme canais oficiais efetivamente disponibilizados; EFD-Reinf somente se aplicável ao escopo; bancos, relógios de ponto, operadoras de benefícios, SST, contabilidade e armazenamento/assinatura de documentos.
- Antes de implementar cada conector, verificar documentação oficial atual, disponibilidade de API/canal, credenciais, homologação, prazos, regras fiscais vigentes e necessidade de especialista. Substituir apenas o adapter mock correspondente; manter contrato, idempotência, auditoria e testes de contrato.

## Entidades e contratos iniciais

`Company`, `Establishment`, `Department`, `JobTitle`, `UserAccount`, `Person`, `Employee`, `EmploymentContract`, `ContractChange`, `Dependent`, `WorkSchedule`, `TimePunch`, `Timesheet`, `Leave`, `VacationPeriod`, `VacationRequest`, `BenefitPlan`, `EmployeeBenefit`, `PayrollPeriod`, `Payroll`, `PayrollItem`, `PayrollRuleVersion`, `PayrollAdjustment`, `Termination`, `OutboxEvent`, `IntegrationAttempt`, `AuditEntry`.

Exemplo de contrato de API: `record EmployeeCreateRequest(String name, String cpf, LocalDate birthDate, UUID establishmentId) {}` com Bean Validation nos componentes; `record EmployeeResponse(UUID id, String name, String maskedCpf, String status) {}`. Usar mapeadores explícitos e testes de contrato OpenAPI quando necessário.

## Forma de execução pela IA

Para cada etapa: (1) propor migrations e API mínima; (2) implementar; (3) executar build/testes; (4) mostrar endpoints, payloads e comandos de execução; (5) registrar limites e TODOs da etapa; (6) avançar só depois de a etapa estar funcional. Escrever testes úteis para isolamento entre empresas, estados, cálculos, arredondamento, históricos, idempotência e fechamento. Não criar regras legais por suposição. Documentar variáveis de ambiente e decisões técnicas em `README.md`.

## Fontes técnicas consultadas

- Spring Boot 4.1.1, starters e testes: https://docs.spring.io/spring-boot/reference/using/build-systems.html
- Spring Boot RestClient: https://docs.spring.io/spring-boot/reference/io/rest-client.html
- springdoc e compatibilidade Spring Boot 4: https://springdoc.org/
