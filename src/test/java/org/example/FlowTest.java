package org.example;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FlowTest {

    @Autowired
    private MockMvcTester mockMvc;

    @Test
    void fullFlow() throws Exception {

        String gymId = createGym("Gym", "street 10", "+48 123 345 567");
        String planId = createPlan(gymId, "Plan1", "PREMIUM", "100.99", "PLN", 1, 2);
        createMember(planId, "Aleksander", "Hlebowicz", "aleksanderhlebowicz@gmail.com");

        assertThat(mockMvc.get().uri("/api/members")).hasStatusOk().bodyJson().isLenientlyEqualTo("""
                [
                    {
                        "email": "aleksanderhlebowicz@gmail.com",
                        "status": "ACTIVE",
                        "planName": "Plan1",
                        "gymName": "Gym"
                    }
                ]
                """);
    }

    @Test
    void revenueReportTest() throws UnsupportedEncodingException {

        String fitLife = createGym("FitLife Center", "street 1", "+48 111 111 111");
        String ironGym = createGym("Iron Gym", "street 2", "+48 222 222 222");

        String eur = createPlan(fitLife, "EUR", "PREMIUM", "2048.00", "EUR", 1, 10);
        String gbp = createPlan(fitLife, "GBP", "PREMIUM", "512.64", "GBP", 1, 10);
        String pln = createPlan(ironGym, "PLN", "BASIC", "1024.00", "PLN", 1, 9);

        createMember(eur, "Aleksander", "Hlebowicz", "aleksanderhlebowicz@gmail.com");
        createMember(gbp, "Paweł", "Hlebowicz", "pawelhlebowicz@gmail.com");
        createMember(pln, "test", "test", "tset@test.com");

        MvcTestResult result = mockMvc.get().uri("/api/reports/revenue").exchange();

        System.out.println(result.getResponse().getContentAsString());

        assertThat(result).hasStatusOk().bodyJson().isLenientlyEqualTo("""
            [
                { "gymName": "FitLife Center", "amount": 2048.00, "currency": "EUR" },
                { "gymName": "FitLife Center", "amount": 512.64, "currency": "GBP" },
                { "gymName": "Iron Gym", "amount": 1024.00, "currency": "PLN" }
            ]
            """);
    }

    @Test
    void createGym_duplicateName() throws UnsupportedEncodingException {

        createGym("Gym", "street 10", "+48 123 345 567");

        assertThat(mockMvc.post().uri("/api/gyms").contentType(MediaType.APPLICATION_JSON).content("""
                    { "name": "Gym", "address": "astreet 11", "phoneNumber": "+48 987 765 543" }
                    """)).hasStatus(HttpStatus.CONFLICT);
    }

    @Test
    void createMember_planFull() throws UnsupportedEncodingException {

        String gymId = createGym("Gym", "street 10", "+48 123 345 567");
        String planId = createPlan(gymId, "Plan1", "PREMIUM", "100.99", "PLN", 1, 1);
        createMember(planId, "Aleksander", "Hlebowicz", "aleksanderhlebowicz@gmail.com");

        assertThat(mockMvc.post().uri("/api/plans/{id}/members", planId).contentType(MediaType.APPLICATION_JSON).content("""
                    { "name": "Paweł", "surname": "Hlebowicz", "email": "pawelhlebowicz@gmail.com" }
                    """)).hasStatus(HttpStatus.CONFLICT);

    }

    @Test
    void cancelMembership_freesSlot() throws UnsupportedEncodingException {

        String gymId = createGym("Gym", "street 10", "+48 123 345 567");
        String planId = createPlan(gymId, "Plan1", "PREMIUM", "100.99", "PLN", 1, 1);
        String memberId = createMember(planId, "Aleksander", "Hlebowicz", "aleksanderhlebowicz@gmail.com");

        assertThat(mockMvc.post().uri("/api/plans/{id}/members", planId).contentType(MediaType.APPLICATION_JSON).content("""
                    { "name": "Paweł", "surname": "Hlebowicz", "email": "pawelhlebowicz@gmail.com" }
                    """)).hasStatus(HttpStatus.CONFLICT);

        assertThat(mockMvc.post().uri("/api/members/{id}/cancel", memberId))
                .hasStatusOk();

        assertThat(mockMvc.post().uri("/api/plans/{id}/members", planId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "name": "Paweł", "surname": "Hlebowicz", "email": "pawelhlebowicz@gmail.com" }
                    """))
                .hasStatus(HttpStatus.CREATED);
    }







    private String createGym(String name, String address, String phone) throws UnsupportedEncodingException {

        String body = """
                { "name": "%s", "address": "%s", "phoneNumber": "%s" }
                """.formatted(name, address, phone);

        MvcTestResult result = mockMvc.post().uri("/api/gyms").contentType(MediaType.APPLICATION_JSON).content(body).exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);

        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    private String createPlan(String gymId, String name, String type, String amount, String currency, int durationMonths, int maxMembers) throws UnsupportedEncodingException {

        String body = """
                { "name": "%s", "type": "%s", "amount": %s, "currency": "%s", "durationMonths": %d, "maxMembers": %d }
                """.formatted(name, type, amount, currency, durationMonths, maxMembers);

        MvcTestResult result = mockMvc.post().uri("/api/gyms/{id}/plans", gymId).contentType(MediaType.APPLICATION_JSON).content(body).exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);

        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    private String createMember(String planId, String name, String surname, String email) throws UnsupportedEncodingException {

        String body = """
                { "name": "%s", "surname": "%s", "email": "%s" }
                """.formatted(name, surname, email);

        MvcTestResult result = mockMvc.post().uri("/api/plans/{id}/members", planId).contentType(MediaType.APPLICATION_JSON).content(body).exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);

        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }
}
