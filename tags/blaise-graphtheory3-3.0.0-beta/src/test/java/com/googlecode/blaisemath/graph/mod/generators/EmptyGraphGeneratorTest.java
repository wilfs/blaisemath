/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.blaisemath.graph.mod.generators;

/*
 * #%L
 * BlaiseGraphTheory
 * --
 * Copyright (C) 2009 - 2015 Elisha Peterson
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.googlecode.blaisemath.graph.Graph;
import com.googlecode.blaisemath.graph.GraphUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author elisha
 */
public class EmptyGraphGeneratorTest {

    @Test
    public void testGetEmptyGraphInstance() {
        System.out.println("getEmptyGraphInstance");
        assertEquals("NODES: [0, 1, 2, 3]  EDGES: 0: [] 1: [] 2: [] 3: []", 
                GraphUtils.printGraph(new EmptyGraphGenerator().generate(new DefaultGeneratorParameters(false,4))));
        assertEquals("NODES: [0, 1, 2, 3]  EDGES: 0: [] 1: [] 2: [] 3: []", 
                GraphUtils.printGraph(new EmptyGraphGenerator().generate(new DefaultGeneratorParameters(true,4))));
        Graph result = new EmptyGraphGenerator().generate(new DefaultGeneratorParameters(true, 10));
        assertEquals(10, result.nodeCount());
        assertEquals(0, result.edgeCount());
        for (int i = 0; i < 10; i++) {
            assertTrue(result.contains(i));
        }
    }

}