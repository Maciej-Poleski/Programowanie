using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class CyclicList<T> : IEquatable<CyclicList<T>>
{
    private readonly SharedData _args;

    public CyclicList(params T[] args)
    {
        _args = new SharedData(args, 0);
    }

    private CyclicList(SharedData data)
    {
        _args = data;
    }

    public int Length
    {
        get { return _args.Length; }
    }

    public T this[int i]
    {
        get { return _args[i%Length]; }
        set { _args[i%Length] = value; }
    }

    #region IEquatable<CyclicList<T>> Members

    public bool Equals(CyclicList<T> other)
    {
        if (ReferenceEquals(other, null))
            return false;
        if (ReferenceEquals(this, other))
            return true;
        if (GetType() != other.GetType())
            return false;
        for (int i = 0; i < Length; ++i)
            if (!ReferenceEquals(_args[i], other._args[i]))
            {
                if (ReferenceEquals(_args[i], null))
                    return false;
                if (!_args[i].Equals(other._args[i]))
                    return false;
            }
        return true;
    }

    #endregion

    public CyclicList<T>[] split(Func<T, int> func)
    {
        var rawResult = new Dictionary<int, List<T>>();
        var keys = new List<int>();
        foreach (T t in _args)
        {
            int type = func(t);
            if (!rawResult.ContainsKey(type))
            {
                rawResult.Add(type, new List<T>());
                keys.Add(type);
            }
            rawResult[type].Add(t);
        }
        var result = new CyclicList<T>[rawResult.Count];
        keys.Sort();
        int i = 0;
        foreach (int v in keys)
        {
            result[i++] = new CyclicList<T>(rawResult[v].ToArray());
        }
        return result;
    }

    public static CyclicList<T> operator <<(CyclicList<T> o, int shift)
    {
        return new CyclicList<T>(new SharedData(o._args.Data, (o._args.Shift + shift)%o.Length));
    }

    public static CyclicList<T> operator >>(CyclicList<T> o, int shift)
    {
        return new CyclicList<T>(new SharedData(o._args.Data, (o._args.Shift - shift)%o.Length));
    }

    public static implicit operator T[](CyclicList<T> o)
    {
        var result = new T[o.Length];
        for (int i = 0; i < o.Length; ++i)
            result[i] = o[i];
        return result;
    }

    public override bool Equals(object obj)
    {
        return Equals(obj as CyclicList<T>);
    }

    public static bool operator ==(CyclicList<T> lhs, CyclicList<T> rhs)
    {
        if (ReferenceEquals(lhs, null))
        {
            if (ReferenceEquals(rhs, null))
                return true;
            return false;
        }
        return lhs.Equals(rhs);
    }

    public static bool operator !=(CyclicList<T> lhs, CyclicList<T> rhs)
    {
        return !(lhs == rhs);
    }

    public override int GetHashCode()
    {
        return _args.Where(arg => !ReferenceEquals(arg, null)).Aggregate(13, (current, arg) => current*31 + arg.GetHashCode());
    }

    public override string ToString()
    {
        var result = new StringBuilder("[");
        foreach (T arg in _args)
        {
            result.Append(arg.ToString()).Append(", ");
        }
        result.Remove(result.Length - 2, 2);
        result.Append("]");
        return result.ToString();
    }

    #region Nested type: SharedData

    private class SharedData : IEnumerable<T>
    {
        internal readonly T[] Data;
        internal readonly int Shift;

        internal SharedData(T[] data, int shift)
        {
            Data = data;
            Shift = ((shift%data.Length) + data.Length)%data.Length;
        }

        internal T this[int index]
        {
            get { return Data[(index + Shift)%Data.Length]; }
            set { Data[(index + Shift)%Data.Length] = value; }
        }

        public int Length
        {
            get { return Data.Length; }
        }

        #region IEnumerable<T> Members

        public IEnumerator<T> GetEnumerator()
        {
            for (int i = 0; i < Length; ++i)
                yield return this[i];
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        #endregion
    }

    #endregion
}