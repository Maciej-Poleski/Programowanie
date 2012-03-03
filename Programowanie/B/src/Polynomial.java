/**
 * User: Maciej Poleski
 * Date: 03.03.12
 * Time: 12:41
 */
public class Polynomial {
    private static class Monomial {
        int coefficient;
        final int exponent;
        Monomial next;
        Monomial back;

        Monomial(int coefficient, int exponent) {
            this.exponent = exponent;
            this.coefficient = coefficient;
        }

        @Override
        public String toString() {
            if (coefficient == 0)
                return "";
            StringBuilder result = new StringBuilder(" ");
            if (coefficient > 0)
                result.append("+ ");
            else
                result.append("- ");
            if (Math.abs(coefficient) != 1 || exponent == 0)
                result.append(Math.abs(coefficient));
            if (exponent > 0) {
                result.append("x");
                if (exponent > 1)
                    result.append("^").append(exponent);
            }
            return result.toString();
        }
    }

    private Monomial polynomialHead = new Monomial(0, 0);
    private Monomial polynomialTail = new Monomial(0, 0);

    {
        polynomialHead.next = polynomialTail;
        polynomialTail.back = polynomialHead;
    }

    private void addToThis(Polynomial polynomial) {
        Monomial resultIterator = polynomialHead.next;
        Monomial inputIterator = polynomial.polynomialHead.next;
        while (resultIterator != polynomialTail && inputIterator != polynomial.polynomialTail) {
            if (resultIterator.exponent < inputIterator.exponent) {
                Monomial temp = new Monomial(inputIterator.coefficient, inputIterator.exponent);
                temp.next = resultIterator;
                temp.back = resultIterator.back;
                temp.next.back = temp;
                temp.back.next = temp;
                inputIterator = inputIterator.next;
            } else if (resultIterator.exponent == inputIterator.exponent) {
                resultIterator.coefficient += inputIterator.coefficient;
                if (resultIterator.coefficient == 0) {
                    Monomial next = resultIterator.next;
                    resultIterator.next.back = resultIterator.back;
                    resultIterator.back.next = resultIterator.next;
                    resultIterator.next = null;
                    resultIterator.back = null;
                    resultIterator = next;
                } else {
                    resultIterator = resultIterator.next;
                }
                inputIterator = inputIterator.next;
            } else {
                resultIterator = resultIterator.next;
            }
        }
        while (inputIterator != polynomial.polynomialTail) {
            Monomial temp = new Monomial(inputIterator.coefficient, inputIterator.exponent);
            temp.next = polynomialTail;
            temp.back = polynomialTail.back;
            temp.next.back = temp;
            temp.back.next = temp;
            inputIterator = inputIterator.next;
        }
    }

    private static Polynomial multiplyUnchecked(Polynomial a, Polynomial b) {
        Polynomial result = new Polynomial(0);
        for (Monomial i = a.polynomialHead.next; i != a.polynomialTail; i = i.next) {
            Polynomial temp = new Polynomial(0);
            for (Monomial j = b.polynomialHead.next; j != b.polynomialTail; j = j.next) {
                Monomial mono = new Monomial(i.coefficient * j.coefficient, i.exponent + j.exponent);
                mono.next = temp.polynomialTail;
                mono.back = temp.polynomialTail.back;
                mono.next.back = mono;
                mono.back.next = mono;
            }
            result.addToThis(temp);
        }
        return result;
    }

    private int length() {
        int r = 0;
        for (Monomial i = polynomialHead.next; i != polynomialTail; i = i.next)
            ++r;
        return r;
    }

    public Polynomial() {
        Monomial monomial = new Monomial(1, 1);
        monomial.next = polynomialTail;
        monomial.back = polynomialHead;
        monomial.next.back = monomial;
        monomial.back.next = monomial;
    }

    public Polynomial(int coefficient) {
        if (coefficient != 0) {
            Monomial monomial = new Monomial(coefficient, 0);
            monomial.next = polynomialTail;
            monomial.back = polynomialHead;
            monomial.next.back = monomial;
            monomial.back.next = monomial;
        }
    }


    public static Polynomial multiply(Polynomial a, Polynomial b) {
        return a.length() < b.length() ? multiplyUnchecked(a, b) : multiplyUnchecked(b, a);
    }

    public long valueAt(long in) {
        long result = 0;
        Monomial monomial = polynomialHead.next;
        for (int i = monomial.exponent; i >= 0; --i) {
            if (monomial.exponent == i) {
                result = in * result + monomial.coefficient;
                monomial = monomial.next;
            } else {
                result *= in;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Monomial i = polynomialHead.next; i != polynomialTail; i = i.next) {
            result.append(i);
        }
        if (result.toString().isEmpty())
            return "";
        if (result.charAt(1) == '+')
            return result.substring(3);
        else
            return "-" + result.substring(3);
    }

    public void shiftOy(int move) {
        Monomial monomial = polynomialTail.back;
        if (monomial != polynomialHead && monomial.exponent == 0) {
            monomial.coefficient += move;
        } else {
            monomial = new Monomial(move, 0);
            monomial.next = polynomialTail;
            monomial.back = polynomialTail.back;
            monomial.next.back = monomial;
            monomial.back.next = monomial;
        }
    }

    public void shiftOx(int move) {
        Polynomial g = new Polynomial();
        g.shiftOy(-move);
        Polynomial result = new Polynomial(0);
        Monomial monomial = polynomialHead.next;
        for (int i = monomial.exponent; i >= 0; --i) {
            if (monomial.exponent == i) {
                result = Polynomial.multiplyUnchecked(g, result);
                result.shiftOy(monomial.coefficient);
                monomial = monomial.next;
            } else {
                result = Polynomial.multiplyUnchecked(g, result);
            }
        }
        polynomialHead = result.polynomialHead;
        polynomialTail = result.polynomialTail;
    }
}
