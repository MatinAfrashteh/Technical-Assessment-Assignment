package com.example.Technical_Assessment_Assignment.ControllerTest;

import com.example.Technical_Assessment_Assignment.Configuration.Configuration;
import com.example.Technical_Assessment_Assignment.Controller.AdminController;
import com.example.Technical_Assessment_Assignment.Service.ClaimService;
import com.example.Technical_Assessment_Assignment.Service.LostItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@Import(Configuration.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LostItemsService lostItemsService;

    @MockitoBean
    private ClaimService claimService;


    @BeforeEach
    void setup() {
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403WhenUserHasNoAccess() throws Exception {
        mockMvc.perform(get("/admin/getClaim"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn401WhenUserHasNoAccess() throws Exception{
        mockMvc.perform(get("/admin/getClaim"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ali",roles = "ADMIN")
    void shouldFormatFileIsInvalid() throws Exception{
        ClassPathResource file = new ClassPathResource("test-file/file.txt");
        MockMultipartFile invalidFile = new MockMultipartFile(
                "file", "file.txt", "text/plain", file.getInputStream()
        );

        mockMvc.perform(multipart("/admin/upload").file(invalidFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Only PDF files are allowed")));

    }

    @Test
    @WithMockUser(username = "ali",roles = "ADMIN")
    void shouldReturnBadRequestWhenPdfContentIsInvalid() throws Exception{
        ClassPathResource file = new ClassPathResource("test-file/lost_items_filtered.pdf");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "lost_items_filtered.pdf","application/plain", file.getInputStream()
        );

        mockMvc.perform(multipart("/admin/upload").file(mockFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Missing or invalid national ID")));


    }

    @Test
    @WithMockUser(username = "ali", roles = "ADMIN")
    void shouldReturnErrorWhenFileIsMissing() throws Exception {
        mockMvc.perform(multipart("/admin/upload"))
                .andExpect(status().isBadRequest());
    }
}




