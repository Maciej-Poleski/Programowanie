using System;

public static class CreateRecurrence
{
    public static RecurrenceFunction zero()
    {
        return x => 0;
    }

    public static RecurrenceFunction successor()
    {
        return x =>
                   {
                       if (x.Length < 1)
                       {
                           return -1;
                       }
                       return x[0] + 1;
                   };
    }

    public static RecurrenceFunction projection(int p)
    {
        return x =>
                   {
                       if (x.Length < p)
                       {
                           return -1;
                       }
                       return x[p];
                   };
    }

    public static RecurrenceFunction composition(RecurrenceFunction f, RecurrenceFunction g)
    {
        return x =>
                   {
                       if (f == null || g == null)
                       {
                           return -1;
                       }
                       var param = new int[f.GetInvocationList().Length];
                       for (int i = 0; i < param.Length; ++i)
                       {
                           try
                           {
                               param[i] = (int) f.GetInvocationList()[i].DynamicInvoke(x);
                               if (param[i] == -1)
                               {
                                   return -1;
                               }
                           }
                           catch (Exception)
                           {
                               return -1;
                           }
                       }
                       try
                       {
                           return g(param);
                       }
                       catch (Exception)
                       {
                           return -1;
                       }
                   };
    }

    public static RecurrenceFunction primitiveRecurrence(RecurrenceFunction g, RecurrenceFunction h)
    {
        RecurrenceFunction result = null;
        result = input =>
                     {
                         try
                         {
                             if (g == null || h == null || input[0]<0)
                             {
                                 return -1;
                             }
                             if (input[0] == 0)
                             {
                                 var x = new int[input.Length - 1];
                                 Array.Copy(input, 1, x, 0, input.Length - 1);
                                 return g(x);
                             }
                             var forH = new int[input.Length + 1];
                             Array.Copy(input, 1, forH, 0, input.Length - 1);
                             --input[0];
                             forH[forH.Length - 2] = input[0];
                             forH[forH.Length - 1] = result(input);
                             return h(forH);
                         }
                         catch (Exception)
                         {
                             return -1;
                         }
                     };
        return result;
    }
}