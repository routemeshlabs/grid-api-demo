package com.mycompany.app;

import org.junit.Test;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import com.gigaspaces.client.WriteModifiers;

import static org.junit.Assert.*;

/**
 * Example test showing different ways to write and read objects from GigaSpaces
 */
public class SpaceWriteExampleTest {

    @Test
    public void demonstrateSpaceOperations() {
        // Create an embedded space for testing
        GigaSpace gigaSpace = new GigaSpaceConfigurer(
            new EmbeddedSpaceConfigurer("testSpace")
        ).gigaSpace();

        try {
            // 1. Basic write and read
            Person person = new Person("test1", "Test User", 25, "Test City");
            gigaSpace.write(person);
            
            Person readPerson = gigaSpace.readById(Person.class, "test1");
            assertNotNull("Person should be found", readPerson);
            assertEquals("Name should match", "Test User", readPerson.getName());

            // 2. Write with lease (TTL)
            Person tempPerson = new Person("temp1", "Temporary User", 30, "Temp City");
            gigaSpace.write(tempPerson, 5000); // 5 seconds lease
            
            // 3. Batch write
            Person[] people = {
                new Person("batch1", "Batch User 1", 20, "City1"),
                new Person("batch2", "Batch User 2", 25, "City2"),
                new Person("batch3", "Batch User 3", 30, "City3")
            };
            gigaSpace.writeMultiple(people);
            
            // Count total objects
            int count = gigaSpace.count(new Person());
            assertTrue("Should have at least 4 people", count >= 4);

            // 4. Update or write (upsert)
            Person updatedPerson = new Person("test1", "Updated Test User", 26, "Updated City");
            gigaSpace.write(updatedPerson, WriteModifiers.UPDATE_OR_WRITE);
            
            Person verifyUpdate = gigaSpace.readById(Person.class, "test1");
            assertEquals("Name should be updated", "Updated Test User", verifyUpdate.getName());
            assertEquals("Age should be updated", (Integer)26, verifyUpdate.getAge());

            // 5. Write if not exists
            Person newPerson = new Person("unique1", "Unique User", 40, "Unique City");
            gigaSpace.write(newPerson, WriteModifiers.WRITE_ONLY);
            
            // Try to write same ID again - should not overwrite
            Person duplicatePerson = new Person("unique1", "Should Not Write", 50, "Should Not Write");
            try {
                gigaSpace.write(duplicatePerson, WriteModifiers.WRITE_ONLY);
                fail("Should throw exception for duplicate write");
            } catch (Exception e) {
                // Expected - cannot write duplicate with WRITE_ONLY
            }
            
            // Verify original is still there
            Person original = gigaSpace.readById(Person.class, "unique1");
            assertEquals("Original should remain", "Unique User", original.getName());

            System.out.println("All write operations completed successfully!");
            
        } finally {
            // Clean up
            gigaSpace.clear(new Person());
        }
    }
}