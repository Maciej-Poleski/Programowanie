using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

public class BlindAbacus : IEnumerable<BlindAbacus.Row>, ICloneable
{
    private readonly int _base;
    private readonly Row[] _rows;

    public BlindAbacus(int @base, int rows)
    {
        _base = @base;
        _rows = new Row[rows];
        for (int i = 0; i < rows; ++i)
        {
            _rows[i] = new Row(@base);
        }
    }

    public Row this[int i]
    {
        get { return _rows[i]; }
    }

    #region ICloneable Members

    public object Clone()
    {
        var result = new BlindAbacus(_base, _rows.Length);
        for (int i = 0; i < _rows.Length; ++i)
        {
            result._rows[i] = (Row) _rows[i].Clone();
        }
        return result;
    }

    #endregion

    #region IEnumerable<Row> Members

    public IEnumerator<Row> GetEnumerator()
    {
        return new RowEnumerator(this);
    }

    IEnumerator IEnumerable.GetEnumerator()
    {
        return GetEnumerator();
    }

    #endregion

    public static explicit operator int(BlindAbacus blindAbacus)
    {
        int result = 0;
        for (int i = blindAbacus._rows.Length - 1; i >= 0; --i)
        {
            result = result*blindAbacus._base + blindAbacus[i].Value;
        }
        return result;
    }

    public static BlindAbacus operator +(BlindAbacus a, BlindAbacus b)
    {
        if (a._base != b._base)
        {
            throw new ComputationException();
        }
        int value = (int) a + (int) b;
        if (value < 0)
        {
            throw new ComputationException();
        }
        return WithBaseAndValue(a._base, value);
    }

    public static BlindAbacus operator -(BlindAbacus a, BlindAbacus b)
    {
        if (a._base != b._base)
        {
            throw new ComputationException();
        }
        int value = (int) a - (int) b;
        if (value < 0)
        {
            throw new ComputationException();
        }
        return WithBaseAndValue(a._base, value);
    }

    public static BlindAbacus operator ++(BlindAbacus a)
    {
        return a + WithBaseAndValue(a._base, 1);
    }

    public static BlindAbacus operator --(BlindAbacus a)
    {
        return a - WithBaseAndValue(a._base, 1);
    }

    public override string ToString()
    {
        var result = new StringBuilder();
        for (int i = _rows.Length - 1; i >= 0; --i)
        {
            result.Append(_rows[i]);
            if (i != 0)
            {
                result.Append('\n');
            }
        }
        return result.ToString();
    }

    private static BlindAbacus WithBaseAndValue(int @base, int value)
    {
        int rows = 0;
        {
            int v = value;
            while (v != 0)
            {
                ++rows;
                v /= @base;
            }
        }
        var result = new BlindAbacus(@base, rows);
        for (int i = 0; i < rows; ++i)
        {
            result[i].add(value%@base);
            value /= @base;
        }
        return result;
    }

    #region Nested type: ComputationException

    public class ComputationException : Exception
    {
    }

    #endregion

    #region Nested type: IncorrectRowValueException

    public class IncorrectRowValueException : Exception
    {
    }

    #endregion

    #region Nested type: Row

    public class Row : ICloneable
    {
        private readonly int _base;
        internal bool Locked;
        internal int Value;

        public Row(int @base)
        {
            _base = @base;
        }

        #region ICloneable Members

        public object Clone()
        {
            return new Row(_base) {Value = Value};
        }

        #endregion

        public void add(int i)
        {
            if (Value + i >= _base || Value + i < 0)
            {
                throw new IncorrectRowValueException();
            }
            Value += i;
        }

        public void remove(int i)
        {
            add(-i);
        }

        public override string ToString()
        {
            var result = new StringBuilder();
            for (int i = 0; i < Value; ++i)
            {
                result.Append('I');
            }
            for (int i = 0; i < _base - 1; ++i)
            {
                result.Append('-');
            }
            for (int i = 0; i < _base - 1 - Value; ++i)
            {
                result.Append('I');
            }
            return result.ToString();
        }
    }

    #endregion

    #region Nested type: RowEnumerator

    private class RowEnumerator : IEnumerator<Row>
    {
        private readonly BlindAbacus _parent;
        private int _current = -1;

        public RowEnumerator(BlindAbacus parent)
        {
            _parent = parent;
        }

        #region IEnumerator<Row> Members

        public void Dispose()
        {
            if (_current >= 0 && _current < _parent._rows.Length)
            {
                _parent._rows[_current].Locked = false;
            }
        }

        public bool MoveNext()
        {
            if (_current >= 0 && _current < _parent._rows.Length)
            {
                _parent._rows[_current].Locked = false;
            }
            if (++_current < _parent._rows.Length)
            {
                if (_parent._rows[_current].Locked)
                {
                    throw new SomeoneThereException();
                }
                _parent._rows[_current].Locked = true;
                return true;
            }
            return false;
        }

        public void Reset()
        {
            if (_current >= 0 && _current < _parent._rows.Length)
            {
                _parent._rows[_current].Locked = false;
            }
            _current = -1;
        }

        public Row Current
        {
            get { return _parent[_current]; }
        }

        object IEnumerator.Current
        {
            get { return Current; }
        }

        #endregion
    }

    #endregion

    #region Nested type: SomeoneThereException

    public class SomeoneThereException : Exception
    {
    }

    #endregion
}