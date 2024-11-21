package robotic.system.loanComponent.service;
;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import robotic.system.loanComponent.domain.dto.LoanComponentDTO;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoanComponentRepositoryTest {

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSumLoanedQuantitiesByComponentId() {
        when(loanComponentRepository.sumLoanedQuantitiesByComponentId(any(UUID.class), any(LoanStatus.class)))
                .thenReturn(5);

        Integer result = loanComponentRepository.sumLoanedQuantitiesByComponentId(UUID.randomUUID(), LoanStatus.RETURNED);

        assertNotNull(result, "Result should not be null");
        verify(loanComponentRepository).sumLoanedQuantitiesByComponentId(any(UUID.class), any(LoanStatus.class));
    }

    @Test
    void testFindByLoanId() {
        when(loanComponentRepository.findByLoanId(anyString()))
                .thenReturn(Optional.of(new LoanComponent()));

        Optional<LoanComponent> result = loanComponentRepository.findByLoanId("test-loan-id");

        assertNotNull(result, "Result should not be null");
        verify(loanComponentRepository).findByLoanId(anyString());
    }

    @Test
    void testFindOverdueLoans() {
        when(loanComponentRepository.findOverdueLoans(any(Date.class), any(LoanStatus.class)))
                .thenReturn(Collections.emptyList());

        List<LoanComponent> result = loanComponentRepository.findOverdueLoans(new Date(), LoanStatus.APPROVED);

        assertNotNull(result, "Result should not be null");
        verify(loanComponentRepository).findOverdueLoans(any(Date.class), any(LoanStatus.class));
    }

    @Test
    void testFindAllLoanComponentsWithDetails() {
        Page<LoanComponentDTO> mockPage = new PageImpl<>(Collections.emptyList());
        when(loanComponentRepository.findAllLoanComponentsWithDetails(any(PageRequest.class)))
                .thenReturn(mockPage);

        Page<LoanComponentDTO> result = loanComponentRepository.findAllLoanComponentsWithDetails(PageRequest.of(0, 10));

        assertNotNull(result, "Result should not be null");
        verify(loanComponentRepository).findAllLoanComponentsWithDetails(any(PageRequest.class));
    }

    @Test
    void testFindLoanById() {
        when(loanComponentRepository.findLoanById(any(UUID.class)))
                .thenReturn(Optional.of(mock(LoanComponentDTO.class)));

        Optional<LoanComponentDTO> result = loanComponentRepository.findLoanById(UUID.randomUUID());

        assertNotNull(result, "Result should not be null");
        verify(loanComponentRepository).findLoanById(any(UUID.class));
    }

    @Test
    void testFindAllLoanComponentsWithFilters() {
        Page<LoanComponentDTO> mockPage = new PageImpl<>(Collections.emptyList());
        when(loanComponentRepository.findAllLoanComponentsWithFilters(
                anyString(), anyString(), anyString(), anyString(), any(PageRequest.class)))
                .thenReturn(mockPage);

        Page<LoanComponentDTO> result = loanComponentRepository.findAllLoanComponentsWithFilters(
                "loan-id", "borrower@example.com", "authorizer@example.com", "component-name", PageRequest.of(0, 10));

        assertNotNull(result, "Result should not be null");
        verify(loanComponentRepository).findAllLoanComponentsWithFilters(
                anyString(), anyString(), anyString(), anyString(), any(PageRequest.class));
    }

    @Test
    void testFindAllLoanComponentsWithAllDetails() {
        Page<LoanComponentDTO> mockPage = new PageImpl<>(Collections.emptyList());
        when(loanComponentRepository.findAllLoanComponentsWithAllDetails(any(PageRequest.class)))
                .thenReturn(mockPage);

        Page<LoanComponentDTO> result = loanComponentRepository.findAllLoanComponentsWithAllDetails(PageRequest.of(0, 10));

        assertNotNull(result, "Result should not be null");
        verify(loanComponentRepository).findAllLoanComponentsWithAllDetails(any(PageRequest.class));
    }
}
