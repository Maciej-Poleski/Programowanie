import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Maciej Poleski
 * Date: 02.03.12
 * Time: 19:46
 */
public class A {
    final private static Pattern setPattern = Pattern.compile("^set\\s+([a-z]+)\\s*=\\s*(%|([a-z]+)|([-+]?\\d+))\\s*$");
    final private static Pattern gtPattern = Pattern.compile("^>\\s+(([-+]?)\\s*(%|([a-z]+)|(\\d+))(\\s*([-+*])\\s*(%|([a-z]+)|(\\d+)))*)\\s*$");
    final private static Pattern clearPattern = Pattern.compile("^clear\\s+([a-z]+)\\s*$");
    final private static Pattern brokenGtPattern = Pattern.compile("^>\\s+.*$");

    private static class Variable {
        final String name;
        BigInteger value;
        Variable next;
        Variable back;

        private Variable(String name, BigInteger value) {
            this.value = value;
            this.name = name;
        }
    }

    private final Variable variableHead = new Variable("", BigInteger.ZERO);
    private final Variable variableTail = new Variable("", BigInteger.ZERO);

    {
        variableHead.next = variableTail;
        variableTail.back = variableHead;
    }

    private Variable findVariableByName(String variableName) {
        for (Variable i = variableHead.next; i != variableTail; i = i.next) {
            if (variableName.equals(i.name))
                return i;
        }
        return null;
    }

    private void insertVariable(Variable variable) {
        variable.next = variableHead.next;
        variable.back = variableHead;
        variable.next.back = variable;
        variable.back.next = variable;
    }

    private void removeVariable(Variable variable) {
        variable.next.back = variable.back;
        variable.back.next = variable.next;
        variable.back = null;
        variable.next = null;
    }

    private void commandSet(String destinationVariableName, String sourceVariableName) {
        Variable destinationVariable = findVariableByName(destinationVariableName);
        Variable sourceVariable = findVariableByName(sourceVariableName);
        if (destinationVariable != null) {
            if (sourceVariable != null) {
                destinationVariable.value = sourceVariable.value;
            } else {
                removeVariable(destinationVariable);
            }
        } else {
            if (sourceVariable != null) {
                insertVariable(new Variable(destinationVariableName, sourceVariable.value));
            }
        }
    }

    private void commandSet(String destinationVariableName, BigInteger value) {
        Variable destinationVariable = findVariableByName(destinationVariableName);
        if (destinationVariable != null) {
            destinationVariable.value = value;
        } else {
            insertVariable(new Variable(destinationVariableName, value));
        }
    }

    private void commandClear(String variableName) {
        Variable variable = findVariableByName(variableName);
        if (variable != null) {
            removeVariable(variable);
        }
    }

    private void commandGt(String command) {
        if (command.charAt(0) != '-' && command.charAt(0) != '+') {
            command = new StringBuilder("+").append(command).toString();
        }
        BigInteger currentResult = BigInteger.ZERO;
        Pattern singleOpcodeAndOperand = Pattern.compile("\\G\\s*([-+*])\\s*(%|(\\d+)|([a-z]+))");
        Matcher matcher = singleOpcodeAndOperand.matcher(command);
        int endOfLastMatch = 0;
        while (matcher.find()) {
            BigInteger value;
            if (matcher.group(3) != null) {
                value = new BigInteger(matcher.group(3));
            } else {
                Variable variable = findVariableByName(matcher.group(2));
                if (variable == null) {
                    System.out.println("UNDEFINED");
                    commandClear("%");
                    return;
                }
                value = variable.value;
            }
            String operation = matcher.group(1);
            if (operation.equals("+")) {
                currentResult = currentResult.add(value);
            } else if (operation.equals("-")) {
                currentResult = currentResult.subtract(value);
            } else {
                currentResult = currentResult.multiply(value);
            }
            endOfLastMatch = matcher.end();
        }
        if (endOfLastMatch != command.length()) {
            System.out.println("ERROR");
            commandClear("%");
        } else {
            System.out.println(currentResult.toString());
            commandSet("%", currentResult);
        }
    }

    public static void main(String... args) {
        Scanner inputScanner = new Scanner(System.in);
        A calculator = new A();
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            Matcher setResult = setPattern.matcher(line);
            Matcher gtResult = gtPattern.matcher(line);
            Matcher clearResult = clearPattern.matcher(line);
            if (clearResult.find()) {
                calculator.commandClear(clearResult.group(1));
            } else if (setResult.find()) {
                if (setResult.group(4) != null) {
                    BigInteger value = new BigInteger(setResult.group(4));
                    calculator.commandSet(setResult.group(1), value);
                } else {
                    calculator.commandSet(setResult.group(1), setResult.group(2));
                }
            } else if (gtResult.find()) {
                calculator.commandGt(gtResult.group(1));
            } else {
                if (brokenGtPattern.matcher(line).matches()) {
                    calculator.commandClear("%");
                }
                System.out.println("ERROR");
            }
        }
    }
}
