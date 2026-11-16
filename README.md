# Departamento Pessoal

## Ambiente

- Java 21
- MySQL 8 em `localhost:3306`
- Perfil padrão: usa `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- JWT: use `APP_SECURITY_JWT_SECRET` com ao menos 32 bytes

## Perfis

- `local`: ativa seed demo com `admin@local` / `admin123`
- `test`: usa H2 em memória

## Execução

```bash
mvn test
```

Forçar migração do banco:

```bash
mvn flyway:migrate -Dflyway.url=jdbc:mysql://localhost:3306/departamento_pessoal?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC -Dflyway.user=root -Dflyway.password=passwd
```

Para rodar local:

```bash
set SPRING_PROFILES_ACTIVE=local
mvn spring-boot:run
```

## Endpoints

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/me`
- `GET/POST/PUT/DELETE /api/v1/companies`
- `GET/POST/PUT/DELETE /api/v1/workers`
