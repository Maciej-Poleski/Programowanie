import java.util.*;

/**
 * User: Maciej Poleski
 * Date: 10.03.12
 * Time: 18:49
 */

public class a implements Iterable<a> {
    @Override
    public Iterator<a> iterator() {
        return new Iterator<a>() {
            List<Integer> data;

            {
                data = new LinkedList<Integer>(a.this.a.store);
            }

            Iterator<Integer> realIterator;

            {
                realIterator = data.iterator();
            }

            @Override
            public boolean hasNext() {
                return realIterator.hasNext();
            }

            @Override
            public a next() {
                return new a(null) {
                    Integer value;

                    {
                        value = realIterator.next();
                    }

                    @Override
                    public String toString() {
                        return "" + value;
                    }
                };
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static class OriginalOrder extends a {
        LinkedList<Integer> store = new LinkedList<Integer>();

        public OriginalOrder() {
            super(null);
            a = this;
        }

        @Override
        public String toString() {
            return store.toString();
        }

        @Override
        public Iterator<a> iterator() {
            return new Iterator<a>() {
                List<Integer> data;

                {
                    data = new LinkedList<Integer>(OriginalOrder.this.store);
                    Collections.sort(data);
                }

                Iterator<Integer> realIterator;

                {
                    realIterator = data.iterator();
                }

                @Override
                public boolean hasNext() {
                    return realIterator.hasNext();
                }

                @Override
                public a next() {
                    return new a(null) {
                        Integer value;

                        {
                            value = realIterator.next();
                        }

                        @Override
                        public String toString() {
                            return "" + value;
                        }
                    };
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    public OriginalOrder a;

    protected a(OriginalOrder a) {
        this.a = null;
    }

    public a() {
        this.a = new OriginalOrder();
    }

    public a a(int value) {
        this.a.store.addLast(value);
        return this;
    }

    public a a(Integer value) {
        this.a.store.addFirst(value);
        return this;
    }

    public Integer a() {
        return this.a.store.pollLast();
    }

    @Override
    public String toString() {
        List<Integer> copy = new ArrayList<Integer>(this.a.store);
        Collections.sort(copy);
        return copy.toString();
    }
}
