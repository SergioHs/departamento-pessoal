package com.experiment.departamentopessoal;

import com.experiment.departamentopessoal.auth.domain.UserAccount;
import com.experiment.departamentopessoal.auth.domain.UserAccountRepository;
import com.experiment.departamentopessoal.auth.domain.UserRole;
import com.experiment.departamentopessoal.company.domain.Company;
import com.experiment.departamentopessoal.company.domain.CompanyRepository;
import com.experiment.departamentopessoal.worker.domain.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthAndCompanyIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final HttpClient client = HttpClient.newHttpClient();
    private UUID companyId;

    @BeforeEach
    void setUp() {
        userAccountRepository.deleteAll();
        workerRepository.deleteAll();
        companyRepository.deleteAll();

        Company company = companyRepository.save(new Company("ACME", null, true));
        companyId = company.getId();
        userAccountRepository.save(new UserAccount(company, "admin@acme.com", passwordEncoder.encode("secret123"), true, Set.of(UserRole.ADMIN)));
    }

    @Test
    void shouldLoginAndAccessMe() throws Exception {
        String token = loginToken();

        HttpResponse<String> response = send("GET", "/api/v1/auth/me", null, bearerHeaders(token));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("\"email\":\"admin@acme.com\"");
    }

    @Test
    void shouldRequireAuthForCompanyCreation() throws Exception {
        HttpResponse<String> response = send("POST", "/api/v1/companies", companyJson("Nova empresa"), jsonHeaders());

        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void shouldCreateCompanyWithAdminToken() throws Exception {
        String token = loginToken();
        HttpResponse<String> response = send("POST", "/api/v1/companies", companyJson("Nova empresa"), bearerHeaders(token));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("\"name\":\"Nova empresa\"");
    }

    @Test
    void shouldNotExposeBulkCompanyNameUpdate() throws Exception {
        String token = loginToken();

        HttpResponse<String> response = send("PUT", "/api/v1/companies/bulk-name",
                "{\"name\":\"Empresa alterada\"}", bearerHeaders(token));

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(companyRepository.findById(companyId)).get().extracting(Company::getName).isEqualTo("ACME");
    }

    @Test
    void shouldDeactivateCompanyWithAdminToken() throws Exception {
        String token = loginToken();

        HttpResponse<String> response = send("DELETE", "/api/v1/companies/" + companyId, null, bearerHeaders(token));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(companyRepository.findById(companyId)).get().extracting(Company::isActive).isEqualTo(false);
    }

    @Test
    void shouldRequireAuthForWorkerCreation() throws Exception {
        HttpResponse<String> response = send("POST", "/api/v1/workers", workerJson(companyId), jsonHeaders());

        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void shouldCreateWorkerWithAdminToken() throws Exception {
        String token = loginToken();
        HttpResponse<String> response = send("POST", "/api/v1/workers", workerJson(companyId), bearerHeaders(token));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("\"fullName\":\"Maria Oliveira\"");
        assertThat(response.body()).contains("\"companyId\":\"" + companyId + "\"");
    }

    @Test
    void shouldFilterWorkersByNameIgnoringCase() throws Exception {
        String token = loginToken();
        createWorker(token, "Maria Oliveira", "12345678901");
        createWorker(token, "Joao Santos", "12345678902");

        HttpResponse<String> response = send("GET", "/api/v1/workers?name=MARIA", null, bearerHeaders(token));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("\"fullName\":\"Maria Oliveira\"");
        assertThat(response.body()).doesNotContain("\"fullName\":\"Joao Santos\"");
    }

    @Test
    void shouldReturnEmptyPageWhenWorkerNameDoesNotMatch() throws Exception {
        String token = loginToken();
        createWorker(token, "Maria Oliveira", "12345678901");

        HttpResponse<String> response = send("GET", "/api/v1/workers?name=Inexistente", null, bearerHeaders(token));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("\"content\":[]");
        assertThat(response.body()).contains("\"totalElements\":0");
    }

    @Test
    void shouldPaginateWorkersAndLimitPageSize() throws Exception {
        String token = loginToken();
        createWorker(token, "Maria Oliveira", "12345678901");
        createWorker(token, "Joao Santos", "12345678902");
        createWorker(token, "Ana Costa", "12345678903");

        HttpResponse<String> firstPage = send("GET", "/api/v1/workers?page=0&size=2", null, bearerHeaders(token));
        HttpResponse<String> secondPage = send("GET", "/api/v1/workers?page=1&size=2", null, bearerHeaders(token));
        HttpResponse<String> defaultPage = send("GET", "/api/v1/workers", null, bearerHeaders(token));
        HttpResponse<String> limitedPage = send("GET", "/api/v1/workers?size=101", null, bearerHeaders(token));

        assertThat(firstPage.statusCode()).isEqualTo(200);
        assertThat(firstPage.body()).contains("\"size\":2", "\"number\":0", "\"totalElements\":3", "\"numberOfElements\":2");
        assertThat(secondPage.statusCode()).isEqualTo(200);
        assertThat(secondPage.body()).contains("\"size\":2", "\"number\":1", "\"numberOfElements\":1");
        assertThat(defaultPage.statusCode()).isEqualTo(200);
        assertThat(defaultPage.body()).contains("\"size\":20", "\"number\":0", "\"totalElements\":3");
        assertThat(limitedPage.statusCode()).isEqualTo(200);
        assertThat(limitedPage.body()).contains("\"size\":100", "\"number\":0");
    }

    private void createWorker(String token, String name, String cpf) throws Exception {
        HttpResponse<String> response = send("POST", "/api/v1/workers", workerJson(companyId, name, cpf), bearerHeaders(token));
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private String loginToken() throws Exception {
        HttpResponse<String> response = send("POST", "/api/v1/auth/login", authJson("admin@acme.com", "secret123"), jsonHeaders());

        assertThat(response.statusCode()).isEqualTo(200);
        Matcher matcher = Pattern.compile("\"accessToken\":\"([^\"]+)\"").matcher(response.body());
        assertThat(matcher.find()).isTrue();
        return matcher.group(1);
    }

    private HttpResponse<String> send(String method, String path, String body, java.util.Map<String, String> headers) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .header("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.forEach(builder::header);
        if (body == null) {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            builder.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    private java.util.Map<String, String> jsonHeaders() {
        return java.util.Map.of("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    private java.util.Map<String, String> bearerHeaders(String token) {
        return java.util.Map.of(
                "Authorization", "Bearer " + token,
                "Content-Type", MediaType.APPLICATION_JSON_VALUE
        );
    }

    private String authJson(String email, String password) {
        return "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
    }

    private String companyJson(String name) {
        return "{\"name\":\"" + name + "\",\"document\":null,\"active\":true}";
    }

    private String workerJson(UUID companyId) {
        return workerJson(companyId, "Maria Oliveira", "12345678901");
    }

    private String workerJson(UUID companyId, String fullName, String cpf) {
        return "{\"companyId\":\"" + companyId + "\",\"fullName\":\"" + fullName + "\",\"cpf\":\"" + cpf + "\",\"rg\":\"1234567\","
                + "\"birthDate\":\"1990-05-20\",\"gender\":\"Feminino\",\"motherName\":\"Ana Oliveira\",\"email\":\"maria@acme.com\","
                + "\"phone\":\"11999999999\",\"zipCode\":\"01001000\",\"street\":\"Rua A\",\"number\":\"100\",\"complement\":\"Apto 12\","
                + "\"district\":\"Centro\",\"city\":\"Sao Paulo\",\"state\":\"SP\",\"pisPasep\":\"12345678901\","
                + "\"ctpsNumber\":\"12345\",\"ctpsSeries\":\"001\",\"admissionDate\":\"2024-01-10\",\"salary\":3500.50,"
                + "\"jobTitle\":\"Analista\",\"department\":\"RH\",\"fgtsOptIn\":true,\"inssNumber\":\"99887766\","
                + "\"dependentsIrCount\":1,\"withholdsIrrf\":true,\"active\":true}";
    }
}
