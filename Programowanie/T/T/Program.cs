using System.Collections.Generic;
using System.Globalization;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;

public class Unique<T> : IXmlSerializable
{
    private static Dictionary<int, object> fromIdToObject = new Dictionary<int, object>();
    private static Dictionary<object, int> fromObjectToId = new Dictionary<object, int>();
    private static int idGenerator;
    private static bool isReading;
    private T _content;
    private int lazyId = -1;

    public Unique()
    {
    }

    public Unique(T content)
    {
        Content = content;
    }

    public T Content
    {
        get
        {
            if (Equals(_content, default(T)) && lazyId != -1)
                _content = (T) fromIdToObject[lazyId];
            return _content;
        }
        set { _content = value; }
    }

    #region IXmlSerializable Members

    public XmlSchema GetSchema()
    {
        return null;
    }

    public void ReadXml(XmlReader reader)
    {
        if (!isReading)
        {
            isReading = true;
            fromIdToObject = new Dictionary<int, object>();
            fromObjectToId = new Dictionary<object, int>();
            idGenerator = 0;
        }
        var serializer = new XmlSerializer(typeof (T));
        reader.MoveToContent();
        string idString = reader.GetAttribute("ID");
        reader.ReadStartElement();
        reader.ReadStartElement();
        if (idString != null)
        {
            int id = int.Parse(idString);
            if (fromIdToObject.ContainsKey(id))
            {
                _content = (T) fromIdToObject[id];
                if (Equals(_content, default(T)))
                {
                    lazyId = id;
                }
                serializer.Deserialize(reader);
            }
            else
            {
                fromIdToObject[id] = _content;
                if (reader.NodeType == XmlNodeType.Text)
                {
                    _content = (T) (object) reader.ReadContentAsString();
                }
                else
                {
                    _content = (T) serializer.Deserialize(reader);
                }
                fromIdToObject[id] = _content;
                fromObjectToId[_content] = id;
            }
        }
        else
        {
            if (reader.NodeType == XmlNodeType.Text)
            {
                _content = (T) (object) reader.ReadContentAsString();
            }
            else
            {
                _content = (T) serializer.Deserialize(reader);
            }
        }

        reader.ReadEndElement();
        reader.ReadEndElement();
    }

    public void WriteXml(XmlWriter writer)
    {
        if (isReading)
        {
            isReading = false;
            fromIdToObject = new Dictionary<int, object>();
            fromObjectToId = new Dictionary<object, int>();
            idGenerator = 0;
        }
        var serializer = new XmlSerializer(typeof (T));
        if (fromObjectToId.ContainsKey(_content))
        {
            writer.WriteAttributeString("ID", fromObjectToId[_content].ToString(CultureInfo.InvariantCulture));
            writer.WriteStartElement("Content");
            serializer.Serialize(writer,default(T));
            writer.WriteEndElement();
        }
        else
        {
            int id = idGenerator++;
            fromObjectToId[_content] = id;
            fromIdToObject[id] = _content;
            writer.WriteAttributeString("ID", fromObjectToId[_content].ToString(CultureInfo.InvariantCulture));
            writer.WriteStartElement("Content");
            serializer.Serialize(writer, _content);
            writer.WriteEndElement();
        }
    }

    #endregion

    public static implicit operator T(Unique<T> o)
    {
        return o.Content;
    }

    public static implicit operator Unique<T>(T o)
    {
        return new Unique<T>(o);
    }
}