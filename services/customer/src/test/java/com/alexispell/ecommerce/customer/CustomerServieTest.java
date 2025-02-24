package com.alexispell.ecommerce.customer;

import com.alexispell.ecommerce.dto.CustomerRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServieTest {

    @InjectMocks
    private CustomerServie customerServie;

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer() {
        // Given
        CustomerRequestDto customerDto = new CustomerRequestDto(
            "John",
            "Doe",
            "johndoe@gmail.com",
                new Address(
                     "Tbilisi",
                     "Shartava",
                     "33",
                     "000123"
                )
        );
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@gmail.com");
        customer.setAddress(
                new Address(
                    "Tbilisi",
                    "Shartava",
                    "33",
                    "000123"
                ));

        Customer savedCustomer = new Customer();
        savedCustomer.setId(new ObjectId("67bb5b69797fd51d8f1bec00"));
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");
        savedCustomer.setEmail("johndoe@gmail.com");
        savedCustomer.setAddress(new Address(
                "Tbilisi",
                "Shartava",
                "33",
                "000123"
        ));

        // Mock the calls
        Mockito.when(customerMapper.toCustomer(customerDto))
                .thenReturn(customer);
        Mockito.when(customerRepository.save(customer))
                .thenReturn(savedCustomer);
        // When
        customerServie.createCustomer(customerDto);
        // Then
        assertEquals(customerDto.firstName(), savedCustomer.getFirstName());
        assertEquals(customerDto.lastName(), savedCustomer.getLastName());
        assertEquals(customerDto.email(), savedCustomer.getEmail());
        assertEquals(customerDto.address().getCity(), savedCustomer.getAddress().getCity());
        assertEquals(customerDto.address().getStreet(), savedCustomer.getAddress().getStreet());
        assertEquals(customerDto.address().getHouseNumber(), savedCustomer.getAddress().getHouseNumber());
        assertEquals(customerDto.address().getZipCode(), savedCustomer.getAddress().getZipCode());
        assertNotNull(savedCustomer.getId());

        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
        Mockito.verify(customerMapper, Mockito.times(1)).toCustomer(customerDto);
    }

    @Test
    void updateCustomer() {
        // Given
        // When
        // Then
    }

    @Test
    void findAllCustomers() {
        // Given
        // When
        // Then
    }

    @Test
    void existsById() {
        // Given
        // When
        // Then
    }

    @Test
    void findById() {
        // Given
        // When
        // Then
    }

    @Test
    void deleteById() {
        // Given
        // When
        // Then
    }
}