/**
 * SemanticArgumentNodeSupport.java
 * Created on Nov 30, 2009
 */

package org.bm.blaise.scribo.parser.semantic;

import org.bm.blaise.scribo.parser.SemanticNode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.tree.TreeNode;

/**
 * <p>
 *    This class provides some basic functionality for semantic nodes, implementing
 *    all features except for the <code>value()</code> method in the
 *    <code>SemanticNode</code> interface, and all features in the
 *    <code>TreeNode</code> interface.
 * </p>
 * <p>
 *    One of the parameters of the class is an array of <i>arguments</i>, whose types
 *    are specified by an array of classes <code>Class[]</code>. The class types must
 *    be supplied within the constructor, and the
 * </p>
 * @author Elisha Peterson
 */
public abstract class SemanticArgumentNodeSupport implements SemanticNode {

    /** The parent object of the node */
    SemanticNode parent;
    /** Argument types */
    Class<?>[] argTypes;
    /** The arguments of the node */
    SemanticNode[] arguments;

    /**
     * Construct the node with multiple arguments.
     * @param argTypes an array of classes representing the required types of arguments
     * @param arguments an array of nodes representing the arguments; the types should be compatible with the <code>argTypes</code> array.
     * @throws IllegalArgumentException if the arguments do not match the supplied argument types
     */
    public SemanticArgumentNodeSupport(Class<?>[] argTypes, SemanticNode... arguments) {
        this.parent = null;
        setArguments(argTypes, arguments);
    }

    /**
     * Sets the arguments. Checks to ensure that the number of arg types is the same as the number of
     * arguments and that the arguments have values that may be appropriately assigned.
     * @param argTypes an array of classes representing the required types of arguments
     * @param arguments an array of nodes representing the arguments; the types should be compatible with the <code>argTypes</code> array.
     * @throws IllegalArgumentException if the arguments do not match the stored argument types
     */
    protected void setArguments(Class<?>[] argTypes, SemanticNode... arguments) {
        Class<?>[] argTypes2 = new Class<?>[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argTypes2[i] = arguments[i].valueType();
        }
        if (!compatibleArguments(argTypes, argTypes2)) {
            throw new IllegalArgumentException("Arguments are not compatible with specified types.");
        }
        this.argTypes = argTypes;
        this.arguments = arguments;
    }

    abstract boolean compatibleArguments(Class<?>[] types1, Class<?>[] types2);

    //
    // SemanticNode & SemanticArgumentNode INTERFACE METHODS
    //

    public Map<String, Class<?>> unknowns() {
        if (getChildCount() == 0) {
            return Collections.EMPTY_MAP;
        } else if (getChildCount() == 1) {
            return arguments[0].unknowns();
        } else {
            Map<String, Class<?>> result = new HashMap<String, Class<?>>();
            for (SemanticNode tn : arguments) {
                result.putAll(tn.unknowns());
            }
            return result;
        }
    }

    //
    // TreeNode INTERFACE METHODS
    //

    public TreeNode getParent() {
        return parent;
    }

    public TreeNode getChildAt(int childIndex) {
        return arguments[childIndex];
    }

    public int getChildCount() {
        return arguments.length;
    }

    public int getIndex(TreeNode node) {
        return Arrays.asList(arguments).indexOf(node);
    }

    public boolean isLeaf() {
        return arguments.length == 0;
    }

    public Enumeration children() {
        return (Enumeration) Arrays.asList(arguments);
    }

    public boolean getAllowsChildren() {
        return true;
    }
}