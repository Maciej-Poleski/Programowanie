/**
 * User: Maciej Poleski
 * Date: 17.03.12
 * Time: 10:53
 */
interface FreakList<T extends Comparable<T>> {
    public T pop() throws AccessDenied, WasWrong, IsEmpty;
}
