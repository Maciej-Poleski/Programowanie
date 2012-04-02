import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * User: Maciej Poleski
 * Date: 24.03.12
 * Time: 12:51
 */
public class Functions {
    public static <T, S, U> Function<T, U> compose(final Function<S, U> function, final Function<T, ? extends S>... functions) throws GenericFunctionsException {
        return new Function<T, U>() {
            T[] input = null;

            {
                int expectedSize = 0;
                T[] firstNonNull = null;
                for (Function<T, ?> f : functions) {
                    if (f == null)
                        throw new GenericFunctionsException();
                    if (f.in() != null) {
                        expectedSize += f.in().length;
                        if (firstNonNull == null)
                            firstNonNull = f.in();
                    }
                }
                if (firstNonNull != null)
                    input = Arrays.copyOf(firstNonNull, expectedSize);

                if (function == null)
                    throw new GenericFunctionsException();
                else if (function.in() == null) {
                    if (functions.length != 0)
                        throw new GenericFunctionsException();
                } else if (function.in().length != functions.length)
                    throw new GenericFunctionsException();
            }

            @Override
            public T[] in() {
                return input;
            }

            @Override
            public U out() {
                if (function.in() == null)
                    return function.out();
                else {
                    int iterator = 0;
                    S[] preparedInput = Arrays.copyOf(function.in(), function.in().length);
                    for (int i = 0; i < functions.length; ++i) {
                        if (functions[i].in() == null)
                            preparedInput[i] = functions[i].out();
                        else {
                            for (int j = 0; j < functions[i].in().length; ++j) {
                                functions[i].in()[j] = input[iterator++];
                            }
                            preparedInput[i] = functions[i].out();
                        }
                    }
                    for (int i = 0; i < function.in().length; ++i)
                        function.in()[i] = preparedInput[i];
                    return function.out();
                }
            }
        };
    }

    public static <T, S> Function<T, S> identifyCoordinates(final Function<T, S> function, final int... coordinates) throws GenericFunctionsException {
        return new Function<T, S>() {
            int from;
            List<Integer> mappings;
            T[] input;

            {
                if (coordinates.length == 0)
                    throw new GenericFunctionsException();
                if (function == null)
                    throw new GenericFunctionsException();
                if (function.in() == null)
                    throw new GenericFunctionsException();
                from = coordinates[0];
                for (int i = 1; i < coordinates.length; ++i)
                    from = Math.min(from, coordinates[i]);
                if (from < 0)
                    throw new GenericFunctionsException();
                int lastRequired = coordinates[0];
                for (int i = 1; i < coordinates.length; ++i)
                    lastRequired = Math.max(lastRequired, coordinates[i]);
                if (lastRequired >= function.in().length)
                    throw new GenericFunctionsException();

                HashSet<Integer> coordinatesUnique = new HashSet<Integer>();
                for (int i : coordinates)
                    coordinatesUnique.add(i);

                mappings = new ArrayList<Integer>();
                for (int coordinate : coordinatesUnique)
                    if (from != coordinate)
                        mappings.add(coordinate);
                input = Arrays.copyOf(function.in(), function.in().length - mappings.size());
            }

            @Override
            public T[] in() {
                return input;
            }

            @Override
            public S out() {
                int currentShift = 0;
                for (int i = 0; i < function.in().length; ++i) {
                    if (mappings.contains(i)) {
                        function.in()[i] = input[from];
                        ++currentShift;
                    } else
                        function.in()[i] = input[i - currentShift];
                }
                return function.out();
            }
        };
    }
}
