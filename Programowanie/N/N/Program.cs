internal class BoolBox
{
    internal bool Value;

    internal BoolBox(bool value)
    {
        Value = value;
    }
}

public class P
{
    private readonly BoolBox value;
    private readonly bool viewNegated;

    public P()
    {
        value = new BoolBox(false);
        viewNegated = true;
    }

    internal P(BoolBox value, bool viewNegated)
    {
        this.value = value;
        this.viewNegated = viewNegated;
    }

    public bool s()
    {
        return value.Value ^ viewNegated;
    }

    #region Nested type: Q

    public class Q
    {
        private readonly BoolBox value;
        private readonly bool viewNegated;

        public Q()
        {
            value = new BoolBox(false);
            viewNegated = false;
        }

        internal Q(BoolBox value, bool viewNegated)
        {
            this.value = value;
            this.viewNegated = viewNegated;
        }

        public bool s()
        {
            return value.Value ^ viewNegated;
        }

        public static explicit operator P(Q o)
        {
            return new P(o.value, !o.viewNegated);
        }

        #region Nested type: R

        public class R
        {
            private readonly BoolBox value;
            private readonly bool viewNegated;

            public R()
            {
                value = new BoolBox(false);
                viewNegated = true;
            }

            internal R(BoolBox value, bool viewNegated)
            {
                this.value = value;
                this.viewNegated = viewNegated;
            }

            public bool s()
            {
                return value.Value ^ viewNegated;
            }

            public static explicit operator Q(R o)
            {
                return new Q(o.value, o.viewNegated);
            }

            public static explicit operator P(R o)
            {
                return new P(o.value, o.viewNegated);
            }

            #region Nested type: S

            public class S
            {
                internal readonly BoolBox value;
                private readonly bool viewNegated;

                public S()
                {
                    value = new BoolBox(false);
                    viewNegated = false;
                }

                public bool s()
                {
                    return value.Value ^ viewNegated;
                }

                public static explicit operator R(S o)
                {
                    return new R(o.value, !o.viewNegated);
                }

                public static explicit operator Q(S o)
                {
                    return new Q(o.value, !o.viewNegated);
                }

                public static explicit operator P(S o)
                {
                    return new P(o.value, !o.viewNegated);
                }
            }

            #endregion
        }

        #endregion
    }

    #endregion
}

public class R
{
    private readonly bool setNegated;
    private readonly BoolBox value;

    private R(BoolBox value, bool setNegated)
    {
        this.value = value;
        this.setNegated = setNegated;
    }

    public bool s
    {
        set { this.value.Value = setNegated ^ value; }
    }

    public static explicit operator R(P.Q.R.S o)
    {
        return new R(o.value, false);
    }

    public static explicit operator S(R o)
    {
        return new S(o.value);
    }
}

public class S
{
    private readonly BoolBox value;

    internal S(BoolBox value)
    {
        this.value = value;
    }

    public int s
    {
        get { return value.Value ? 1 : 0; }
        set { this.value.Value = (value == 1); }
    }
}