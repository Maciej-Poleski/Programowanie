/**
 * User: Maciej Poleski
 * Date: 15.03.12
 * Time: 17:53
 */

class FreakListBypassAccessDenied<T extends Comparable<T>> implements FreakList<T> {
    private final FreakList<T> freakList;

    public FreakListBypassAccessDenied(FreakList<T> freakList) {
        this.freakList = freakList;
    }

    @Override
    public T pop() throws WasWrong, IsEmpty {
        for (; ; ) {
            try {
                return freakList.pop();
            } catch (IsEmpty e) {
                throw e;
            } catch (WasWrong e) {
                throw e;
            } catch (AccessDenied e) {
                // DO NOTHING
            }
        }
    }
}

class FreakListBypassWasWrong<T extends Comparable<T>> implements FreakList<T> {
    private T state;
    private final FreakListBypassAccessDenied<T> freakList;

    public FreakListBypassWasWrong(FreakListBypassAccessDenied<T> tFreakList) {
        this.freakList = tFreakList;
    }

    @Override
    public T pop() throws IsEmpty {
        if (state == null) {
            try {
                state = freakList.pop();
            } catch (IsEmpty e) {
                try {
                    state = freakList.pop();
                } catch (IsEmpty e2) {
                    throw e2;
                } catch (WasWrong e2) {
                    try {
                        state = freakList.pop();
                    } catch (WasWrong e3) {
                        // EMPTY
                    }
                }
            } catch (WasWrong e) {
                // EMPTY
            }
        }
        try {
            T newState = freakList.pop();
            T result = state;
            state = newState;
            return result;
        } catch (IsEmpty e) {
            try {
                T newState = freakList.pop();
                T result = state;
                state = newState;
                return result;
            } catch (IsEmpty e2) {
                if (state != null) {
                    try {
                        return state;
                    } finally {
                        state = null;
                    }
                } else {
                    throw e2;
                }
            } catch (WasWrong e2) {
                T result = state;
                state = null;
                return result;
            }
        } catch (WasWrong e) {
            try {
                return freakList.pop();
            } catch (WasWrong e2) {
                // EMPTY
            }
        }
        return null;
    }
}

public class Freak {
    public static <T extends Comparable<T>> void merge(FreakList<T> L1, FreakList<T> L2) {
        FreakListBypassWasWrong<T> list1 = new FreakListBypassWasWrong<T>(new FreakListBypassAccessDenied<T>(L1));
        FreakListBypassWasWrong<T> list2 = new FreakListBypassWasWrong<T>(new FreakListBypassAccessDenied<T>(L2));
        T left = null;
        T right = null;
        for (; ; ) {
            if (left == null) {
                try {
                    left = list1.pop();
                } catch (IsEmpty e) {
                    break;
                }
            }
            if (right == null) {
                try {
                    right = list2.pop();
                } catch (IsEmpty e) {
                    for (; ; ) {
                        try {
                            System.out.println(left);
                            left = list1.pop();
                        } catch (IsEmpty e2) {
                            return;
                        }
                    }
                }
            }
            if (right != null) {
                // left!=null
                if (left.compareTo(right) <= 0) {
                    System.out.println(left);
                    left = null;
                } else {
                    System.out.println(right);
                    right = null;
                }
            }
        }
        if (right != null) {
            System.out.println(right);
        }
        for (; ; ) {
            try {
                right = list2.pop();
                System.out.println(right);
            } catch (IsEmpty e) {
                break;
            }
        }
    }
}
