package RuleEngine1;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class RuleEngine {
    // Creates a rule from a string and returns the root Node of the AST
    public Node createRule(String ruleString) {
        // Simple parsing logic for demonstration purposes (expand as needed)
        String[] tokens = ruleString.split(" ");
        Node root = null;
        List<Node> stack = new ArrayList<>();

        for (String token : tokens) {
            if (token.equals("AND") || token.equals("OR")) {
                Node right = stack.remove(stack.size() - 1);
                Node left = stack.remove(stack.size() - 1);
                Node operatorNode = new Node("operator", left, right, token);
                stack.add(operatorNode);
            } else {
                // Assuming operand tokens are of the form "age > 30"
                String[] parts = token.split("(?<=[<>=])|(?=[<>=])");
                Node operandNode = new Node("operand", null, null, token);
                stack.add(operandNode);
            }
        }

        return stack.get(0); // Return the root node
    }

    // Combine multiple rules into a single AST
    public Node combineRules(List<String> rules) {
        Node combinedRoot = null;
        for (String rule : rules) {
            Node currentRoot = createRule(rule);
            if (combinedRoot == null) {
                combinedRoot = currentRoot;
            } else {
                combinedRoot = new Node("operator", combinedRoot, currentRoot, "AND"); // Combine using AND for simplicity
            }
        }
        return combinedRoot;
    }

    // Evaluate the combined rule against the provided data
    public boolean evaluateRule(Node root, JSONObject data) {
        if (root.isOperator()) {
            boolean leftEval = evaluateRule(root.left, data);
            boolean rightEval = evaluateRule(root.right, data);
            return root.value.equals("AND") ? leftEval && rightEval : leftEval || rightEval;
        } else {
            String[] parts = root.value.split("(?<=[<>=])|(?=[<>=])");
            String attribute = parts[0].trim();
            String operator = parts[1].trim();
            String comparisonValue = parts[2].trim();

            if (!data.has(attribute)) {
                return false;
            }

            // Perform the comparison
            Object dataValue = data.get(attribute);
            switch (operator) {
                case ">":
                    return ((Number) dataValue).doubleValue() > Double.parseDouble(comparisonValue);
                case "<":
                    return ((Number) dataValue).doubleValue() < Double.parseDouble(comparisonValue);
                case "=":
                    return dataValue.toString().equals(comparisonValue);
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
        }
    }
}

