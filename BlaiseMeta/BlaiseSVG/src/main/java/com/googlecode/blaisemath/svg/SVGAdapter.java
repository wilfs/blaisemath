/**
 * SVGAdapter.java
 * Created Sep 26, 2014
 */

package com.googlecode.blaisemath.svg;

/*
 * #%L
 * BlaiseGraphics
 * --
 * Copyright (C) 2009 - 2014 Elisha Peterson
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

/**
 * <p>
 *   Converts an SVG object to/from a Java graphics object.
 * </p>
 * @param <A> type of SVG object
 * @param <B> type of Java graphics object
 * @author elisha
 */
public interface SVGAdapter<A extends SVGObject, B> {

    /**
     * Converts a Java graphics object to an SVG object.
     * @param gr the Java graphics object
     * @return the SVG object
     */
    A toSVG(B gr);
    
    /**
     * Converts an SVG object to a Java graphics object
     * @param svg the SVG object
     * @return the Java object
     */
    B toGraphics(A svg);
    
}