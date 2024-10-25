package Main1;
import RuleEngine1.*;
import org.json.JSONObject;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        RuleEngine engine = new RuleEngine();

        // Test creating rules
        String rule1 = "age > 30 AND department = 'Sales'";
        String rule2 = "age < 25 AND department = 'Marketing'";

        Node ast1 = engine.createRule(rule1);
        Node ast2 = engine.createRule(rule2);

        System.out.println("AST for Rule 1: " + ast1);
        System.out.println("AST for Rule 2: " + ast2);

        // Test combining rules
        Node combinedAST = engine.combineRules(Arrays.asList(rule1, rule2));
        System.out.println("Combined AST: " + combinedAST);

        // Test evaluation
        JSONObject testData1 = new JSONObject();
        testData1.put("age", 35);
        testData1.put("department", "Sales");

        boolean result1 = engine.evaluateRule(combinedAST, testData1);
        System.out.println("Evaluation Result 1: " + result1); // Expected: true

        JSONObject testData2 = new JSONObject();
        testData2.put("age", 24);
        testData2.put("department", "Marketing");

        boolean result2 = engine.evaluateRule(combinedAST, testData2);
        System.out.println("Evaluation Result 2: " + result2); // Expected: true

        JSONObject testData3 = new JSONObject();
        testData3.put("age", 20);
        testData3.put("department", "Sales");

        boolean result3 = engine.evaluateRule(combinedAST, testData3);
        System.out.println("Evaluation Result 3: " + result3); // Expected: false
    }
}
