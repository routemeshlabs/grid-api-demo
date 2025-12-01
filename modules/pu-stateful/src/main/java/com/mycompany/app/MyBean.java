/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany.app;

import org.slf4j.*;
import jakarta.annotation.*;

import org.openspaces.core.*;
import org.openspaces.core.space.status.*;
import org.openspaces.core.cluster.*;
import com.gigaspaces.client.WriteModifiers;
import org.springframework.beans.factory.annotation.Value;

public class MyBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource // Injected by Spring
    private GigaSpace gigaSpace;

    @ClusterInfoContext //Injected by GigaSpaces
    private ClusterInfo clusterInfo;

    @Value("${space.name}") // Injected by Spring
    private String spaceName;

    private String id;

    @PostConstruct
    public void initialize() {
        id = gigaSpace.getSpaceName() + "[" + (clusterInfo != null ? clusterInfo.getSuffix() : "non-clustered") + "]";
        logger.info("Initialized {}", id);
        // NOTE: This method is called for both primary and backup instances.
        // If you wish to do something for primaries only, see @SpaceStatusChanged

    }

    @SpaceStatusChanged
    public void onSpaceStatusChange(SpaceStatusChangedEvent event) {
        logger.info("Space {} is {}", id, event.getSpaceMode());
        if (event.isActive()) {
            // If you have initialization code for active instances only, put it here.
            demonstrateWriteOperations();
        } else {
            // Space is backup, or space is primary but suspended.
            // If your code should only run when the space is active, you should deactivate it here.
        }
    }
    
    /**
     * Demonstrates various ways to write objects to the GigaSpaces space
     */
    private void demonstrateWriteOperations() {
        try {
            logger.info("Demonstrating write operations to space...");
            
            // 1. Simple write operation
            Person person1 = new Person("1", "John Doe", 30, "New York");
            gigaSpace.write(person1);
            logger.info("Written person: {}", person1);
            
            // 2. Write with lease (object expires after 60 seconds)
            Person person2 = new Person("2", "Jane Smith", 25, "London");
            gigaSpace.write(person2, 60000); // 60 seconds lease
            logger.info("Written person with lease: {}", person2);
            
            // 3. Write multiple objects at once
            Person[] people = {
                new Person("3", "Bob Johnson", 35, "Paris"),
                new Person("4", "Alice Brown", 28, "Tokyo"),
                new Person("5", "Charlie Wilson", 42, "Sydney")
            };
            gigaSpace.writeMultiple(people);
            logger.info("Written {} people in batch", people.length);
            
            // 4. Update or write (upsert) - will update if exists, write if not
            Person existingPerson = new Person("1", "John Doe Updated", 31, "Boston");
            gigaSpace.write(existingPerson, WriteModifiers.UPDATE_OR_WRITE);
            logger.info("Updated existing person: {}", existingPerson);
            
            // 5. Check how many objects are in space
            int count = gigaSpace.count(new Person());
            logger.info("Total persons in space: {}", count);
            
        } catch (Exception e) {
            logger.error("Error during write operations", e);
        }
    }

    @PreDestroy
    public void close() {
        logger.info("Closing {}", id);
    }
}
