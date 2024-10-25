package RuleEngine1;
import java.util.Objects;

// Node class for AST
public class Node {
    public String type; // "operator" or "operand"
    public Node left; // left child
    public Node right; // right child
    public String value; // value for operand nodes (e.g., number for comparisons)

    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    // Override toString for better visualization
    @Override
    public String toString() {
        if (type.equals("operand")) {
            return value;
        }
        return "(" + left.toString() + " " + value + " " + right.toString() + ")";
    }

    // Utility method to check if the node is an operator
    public boolean isOperator() {
        return "operator".equals(type);
    }
}
