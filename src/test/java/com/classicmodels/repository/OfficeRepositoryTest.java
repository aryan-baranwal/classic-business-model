package com.classicmodels.repository;

import com.classicmodels.entity.Office;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class OfficeRepositoryTest {

    @Autowired
    private OfficeRepository officeRepository;

    private Office createOffice() {

        Office office = new Office();

        office.setOfficeCode("101");
        office.setCity("Chennai");
        office.setPhone("1234567890");
        office.setAddressLine1("Anna Nagar");
        office.setAddressLine2("Near Metro");
        office.setState("Tamil Nadu");
        office.setCountry("India");
        office.setPostalCode("600001");
        office.setTerritory("South");

        return office;
    }

    @Test
    @Order(1)
    @DisplayName("Test Save Office")
    void testSaveOffice() {

        Office office = createOffice();

        Office savedOffice = officeRepository.save(office);

        assertNotNull(savedOffice);
        assertEquals("101", savedOffice.getOfficeCode());
    }

    @Test
    @Order(2)
    @DisplayName("Test Find Office By Id")
    void testFindOfficeById() {

        Office office = createOffice();
        officeRepository.save(office);

        Optional<Office> foundOffice =
                officeRepository.findById("101");

        assertTrue(foundOffice.isPresent());
    }

    @Test
    @Order(3)
    @DisplayName("Test Find All Offices")
    void testFindAllOffices() {

        Office office = createOffice();
        officeRepository.save(office);

        List<Office> offices =
                officeRepository.findAll();

        assertFalse(offices.isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("Test Update Office")
    void testUpdateOffice() {

        Office office = createOffice();
        officeRepository.save(office);

        Office savedOffice =
                officeRepository.findById("101").get();

        savedOffice.setCity("Bangalore");

        Office updatedOffice =
                officeRepository.save(savedOffice);

        assertEquals(
                "Bangalore",
                updatedOffice.getCity()
        );
    }

    @Test
    @Order(5)
    @DisplayName("Test Delete Office")
    void testDeleteOffice() {

        Office office = createOffice();
        officeRepository.save(office);

        officeRepository.deleteById("101");

        Optional<Office> deletedOffice =
                officeRepository.findById("101");

        assertFalse(deletedOffice.isPresent());
    }

    @Test
    @Order(6)
    @DisplayName("Test Exists By Id")
    void testExistsById() {

        Office office = createOffice();
        officeRepository.save(office);

        boolean exists =
                officeRepository.existsById("101");

        assertTrue(exists);
    }

    @Test
    @Order(7)
    @DisplayName("Test Office Not Found By Id")
    void testOfficeNotFoundById() {

        Optional<Office> office =
                officeRepository.findById("999");

        assertFalse(office.isPresent());
    }

    @Test
    @Order(8)
    @DisplayName("Test Count Offices")
    void testCountOffices() {

        Office office = createOffice();
        officeRepository.save(office);

        long count = officeRepository.count();

        assertTrue(count > 0);
    }

    @Test
    @Order(9)
    @DisplayName("Test Save Multiple Offices")
    void testSaveMultipleOffices() {

        Office office1 = createOffice();

        Office office2 = new Office();

        office2.setOfficeCode("102");
        office2.setCity("Delhi");
        office2.setPhone("9876543210");
        office2.setAddressLine1("Connaught Place");
        office2.setAddressLine2("Near Mall");
        office2.setState("Delhi");
        office2.setCountry("India");
        office2.setPostalCode("110001");
        office2.setTerritory("North");

        officeRepository.save(office1);
        officeRepository.save(office2);

        List<Office> offices =
                officeRepository.findAll();

        assertEquals(2, offices.size());
    }

    @Test
    @Order(10)
    @DisplayName("Test Delete Non Existing Office")
    void testDeleteNonExistingOffice() {

        assertDoesNotThrow(() ->
                officeRepository.deleteById("999")
        );
    }
}