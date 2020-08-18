package com.uyth.gryttr.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionControllerTest {

    @Autowired
    private CollectionController collectionController;

    @Test
    public void testSanitizeParameters() {
        assertThrows(IllegalArgumentException.class, () -> collectionController.sanitizeParameters(null, "5A+", null));
        assertThrows(IllegalArgumentException.class, () -> collectionController.sanitizeParameters(null, "6A+", "6A"));
        assertThrows(IllegalArgumentException.class, () -> collectionController.sanitizeParameters("illegalsort", null, null));
    }

    @Test
    public void testHasEqualOrGreaterGradeValue() {
        assertFalse(collectionController.hasEqualOrGreaterGradeValue("6A+", "6A"));
        assertTrue(collectionController.hasEqualOrGreaterGradeValue("6A", "6A"));
        assertTrue(collectionController.hasEqualOrGreaterGradeValue("5+", "6A"));
    }
}
