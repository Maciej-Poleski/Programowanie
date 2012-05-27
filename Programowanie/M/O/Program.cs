using System;
using System.Text;

public class Poset : ICloneable
{
    private bool[,] _state;

    public Poset(int n)
    {
        _state = new bool[n,n];
        Size = n;
        for (int i = 0; i < n; ++i)
        {
            _state[i, i] = true;
        }
    }

    private int Size { get; set; }

    private bool this[int i, int j]
    {
        get { return _state[i, j]; }
        set { _state[i, j] = value; }
    }

    #region ICloneable Members

    public object Clone()
    {
        var result = new Poset(Size);
        for (int i = 0; i < Size; ++i)
        {
            for (int j = 0; j < Size; ++j)
            {
                result[i, j] = _state[i, j];
            }
        }
        return result;
    }

    #endregion

    public void add(int x, int y)
    {
        if (x >= Size || y >= Size)
        {
            throw new ArgumentOutOfRangeException();
        }
        var newPoset = (Poset) Clone();
        newPoset[x, y] = true;
        newPoset = newPoset.TrancitiveClosure();
        if (!newPoset.IsValid())
        {
            throw new ArithmeticException();
        }
        _state = newPoset._state;
    }

    public static Poset operator ~(Poset poset)
    {
        var result = new Poset(poset.Size);
        for (int i = 0; i < poset.Size; ++i)
        {
            for (int j = 0; j < poset.Size; ++j)
            {
                if (poset[i, j])
                {
                    result[j, i] = true;
                }
            }
        }
        return result;
    }

    public static Poset operator ++(Poset poset)
    {
        var result = new Poset(poset.Size + 1);
        for (int i = 0; i < poset.Size; ++i)
        {
            for (int j = 0; j < poset.Size; ++j)
            {
                result[i, j] = poset[i, j];
            }
        }
        for (int i = 0; i < poset.Size; ++i)
        {
            result[i, poset.Size] = true;
        }
        return result;
    }

    public static Poset operator --(Poset poset)
    {
        var result = new Poset(poset.Size - 1);
        for (int i = 0; i < result.Size; ++i)
        {
            for (int j = 0; j < result.Size; ++j)
            {
                result[i, j] = poset[i, j];
            }
        }
        return result;
    }

    public static Poset operator +(Poset a, Poset b)
    {
        var result = new Poset(Math.Max(a.Size, b.Size));
        for (int i = 0; i < a.Size; ++i)
        {
            for (int j = 0; j < a.Size; ++j)
            {
                result[i, j] = a[i, j];
            }
        }
        for (int i = 0; i < b.Size; ++i)
        {
            for (int j = 0; j < b.Size; ++j)
            {
                if (b._state[i, j])
                {
                    result[i, j] = b[i, j];
                }
            }
        }
        result = result.TrancitiveClosure();
        if (!result.IsValid())
        {
            throw new ArithmeticException();
        }

        return result;
    }

    public static Poset operator -(Poset a, Poset b)
    {
        var result = new Poset(a.Size);
        for (int i = 0; i < a.Size; ++i)
        {
            for (int j = 0; j < a.Size; ++j)
            {
                result[i, j] = a[i, j];
            }
        }
        for (int i = 0; i < Math.Min(result.Size, b.Size); ++i)
        {
            for (int j = 0; j < Math.Min(result.Size, b.Size); ++j)
            {
                if (b[i, j])
                {
                    result[i, j] = false;
                }
            }
        }
        for (int i = 0; i < result.Size; ++i)
        {
            result[i, i] = true;
        }
        result = result.TrancitiveClosure();
        return result;
    }

    public static Poset operator *(Poset a, Poset b)
    {
        int size = a.Size*b.Size;
        var result = new Poset(size);
        for (int i = 0; i < size; ++i)
        {
            for (int j = 0; j < size; ++j)
            {
                if (a[i%a.Size, j%a.Size] && b[i/a.Size, j/a.Size])
                {
                    result[i, j] = true;
                }
            }
        }
        return result;
    }

    public override string ToString()
    {
        var result = new StringBuilder();
        for (int i = 0; i < Size; ++i)
        {
            result.Append("Vertex " + i + ":");
            for (int j = 0; j < Size; ++j)
            {
                if (i == j)
                {
                    continue;
                }
                if (_state[j, i])
                {
                    result.Append(' ').Append(j);
                }
            }
            result.Append('\n');
        }
        return result.ToString();
    }

    private Poset TrancitiveClosure()
    {
        var result = (Poset) Clone();
        for (int k = 0; k < Size; ++k)
        {
            for (int i = 0; i < Size; ++i)
            {
                for (int j = 0; j < Size; ++j)
                {
                    if (result[i, k] && result[k, j])
                    {
                        result[i, j] = true;
                    }
                }
            }
        }
        return result;
    }

    private bool IsValid()
    {
        for (int i = 0; i < Size; ++i)
        {
            for (int j = i + 1; j < Size; ++j)
            {
                if (_state[i, j] && _state[j, i])
                    return false;
            }
        }
        return true;
    }
}