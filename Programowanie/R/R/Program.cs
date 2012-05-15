using System;
using System.Collections.Generic;

public static class EZStorage
{
    private static readonly Dictionary<Type, Stack<object>> Stacks = new Dictionary<Type, Stack<object>>();

    public static T StoreCT<T>(this T o)
    {
        if (!Stacks.ContainsKey(typeof (T)))
        {
            Stacks[typeof (T)] = new Stack<object>();
        }
        Stacks[typeof (T)].Push(o);
        return o;
    }

    public static IEnumerable<T> StoreCT<T>(this IEnumerable<T> o)
    {
        foreach (T obj in o)
        {
            obj.StoreCT();
        }
        return o;
    }

    public static T RestoreCT<T>(this T t)
    {
        if (!Stacks.ContainsKey(typeof (T)))
        {
            throw new EmptyException();
        }
        var result = (T) Stacks[typeof (T)].Pop();
        if (Stacks[typeof (T)].Count == 0)
        {
            Stacks.Remove(typeof (T));
        }
        return result;
    }

    public static IEnumerable<T> RestoreCT<T>(this IEnumerable<T> t)
    {
        var result = new List<T>();
        if (!Stacks.ContainsKey(typeof (T)))
        {
            return result;
        }
        for (;;)
        {
            result.Add((T) Stacks[typeof (T)].Pop());
            if (Stacks[typeof (T)].Count == 0)
            {
                Stacks.Remove(typeof (T));
                break;
            }
        }
        return result;
    }

    public static T StoreRT<T>(this T o)
    {
        if (!Stacks.ContainsKey(o.GetType()))
        {
            Stacks[o.GetType()] = new Stack<object>();
        }
        Stacks[o.GetType()].Push(o);
        return o;
    }

    public static T RestoreRT<T>(this T t)
    {
        if (!Stacks.ContainsKey(t.GetType()))
        {
            throw new EmptyException();
        }
        var result = (T) Stacks[t.GetType()].Pop();
        if (Stacks[t.GetType()].Count == 0)
        {
            Stacks.Remove(t.GetType());
        }
        return result;
    }

    #region Nested type: EmptyException

    public class EmptyException : Exception
    {
    }

    #endregion
}