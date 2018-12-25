package com.tydic.traffic.tafa.utils.xml;

import com.tydic.traffic.tafa.utils.file.FileUtils;
import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName:XmlUtils <br/>
 */
public class XmlUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(XmlUtils.class);

	private static final Map<Class<?>, JAXBContext> JAXB_CONTEXT_CACHE = new ConcurrentHashMap<Class<?>, JAXBContext>();

	/**
	 * 
	 * parseForStaxEvent:(StaxEvent方式解析XML，返回指定节点的Value). <br/>
	 * 形如：<author>Giada De Laurentiis</author>
	 */
	public static List<String> parseForStaxEvent(String xmlFile, String nodeName) {
		List<String> list = new LinkedList<String>();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader reader = null;
		try {
			reader = factory.createXMLEventReader(new FileReader(xmlFile));
			while (reader.hasNext()) {
				XMLEvent event = reader.nextEvent();
				if (event.isStartElement()) {
					String name = event.asStartElement().getName().toString();
					if (name.equals(nodeName)) {
						list.add(reader.getElementText());
					}
				}
			}
		} catch (FileNotFoundException | XMLStreamException e) {
			logger.error(e.getMessage());
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (XMLStreamException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * parseAttrForStaxEvent:(StaxEvent方式解析XML，返回指定节点属性名及Value). <br/>
	 * 形如：<user name="Bob" age="45" gender="male" />
	 * 
	 * @param xmlFile
	 * @param nodeName
	 * @return List<String> 形如：[age=45,name=Bob,gender=male]
	 * @since 1.0
	 */
	public static List<String> parseAttrForStaxEvent(String xmlFile,
                                                     String nodeName) {
		List<String> list = new LinkedList<String>();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = factory.createXMLEventReader(new FileReader(xmlFile));
			while (reader.hasNext()) {
				XMLEvent event = reader.nextEvent();
				if (event.isStartElement()) {
					StartElement startEle = event.asStartElement();
					if (nodeName.equals(startEle.getName().toString())) {
						Iterator it = startEle.getAttributes();
						sb.delete(0, sb.length());
						sb.append("[");
						while (it.hasNext()) {
							Attribute attr = (Attribute) it.next();
							sb.append(attr.getName());
							sb.append("=");
							sb.append(attr.getValue());
							sb.append(",");
						}
						list.add(sb.substring(0, sb.length() - 1) + "]");
					}
				}
			}
		} catch (FileNotFoundException | XMLStreamException e) {
			logger.error(e.getMessage()	);
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (XMLStreamException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * parse:(返回Document文档对象). <br/>
	 * 
	 * @param filePath
	 * @return Document
	 * @since 1.0
	 */
	public static Document getDocument(String filePath) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			document = builder.parse(new File(filePath));
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
		} catch (SAXException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return document;
	}

	/**
	 * 
	 * parseForDom:(Dom方式解析XML。适用于小型 XML文件解析、需要全解析的场景). <br/>
	 * 
	 * @param filePath
	 *            xml文件
	 * @param tagName
	 *            结点标识名
	 * @return NodeList
	 * @since 1.0
	 */
	public static NodeList parseForDom(String filePath, String tagName) {
		NodeList nodeList = null;
		try {
			Document document = getDocument(filePath);
			Element rootElement = document.getDocumentElement();
			NodeList nodes = rootElement.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element child = (Element) node;
				}
			}
			nodeList = rootElement.getElementsByTagName(tagName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return nodeList;
	}

	/**
	 * 将XML解析为JavaBean（不验证XML）
	 * 
	 * @param xml
	 *            XML文档内容
	 * @param clazz
	 *            XML文档根节点Class
	 * @return 解析结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(String xml, Class<T> clazz) {
		if (StringUtils.isEmpty(xml)) {
			return null;
		}

		Object object = null;

		try {
			JAXBContext jaxbContext = getJAXBContext(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			object = unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

		return (T) object;
	}

	/**
	 * 将XML文件解析为JavaBean（不验证XML）
	 * 
	 * @param xmlFile
	 *            XML文件
	 * @param clazz
	 *            XML文档根节点Class
	 * @return 解析结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(File xmlFile, Class<T> clazz) {
		if (xmlFile == null) {
			return null;
		}

		Object object = null;

		try {
			JAXBContext jaxbContext = getJAXBContext(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			object = unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

		return (T) object;
	}

	/**
	 * 将XML文件解析为JavaBean（不验证XML）
	 * 
	 * @param xmlFile
	 *            XML文件
	 * @param clazz
	 *            XML文档根节点Class
	 * @return 解析结果
	 */
	public static <T> T unmarshal(InputStream xmlFile, Class<T> clazz) {
		if (xmlFile == null) {
			return null;
		}

		Object object = null;

		try {
			JAXBContext jaxbContext = getJAXBContext(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			object = unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

		return (T) object;
	}

	/**
	 * 
	 * parse:(将JavaBean转换为XML文档). JavaBean无注解<br/>
	 * 
	 * @param declaredType
	 *            XML文档根节点Class
	 * @param value
	 *            待转换的JavaBean对象
	 * @return String 生成的XML文档内容
	 * @since 1.0
	 */
	public static <T> String marshal(Class<T> declaredType, T value) {
		if (value == null) {
			return StringUtils.EMPTY;
		}

		StringWriter out = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(declaredType);

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			JAXBElement<T> jaxbElement = new JAXBElement<T>(new QName(null,
					declaredType.getSimpleName()), declaredType, value);
			marshaller.marshal(jaxbElement, out);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return out.toString();
	}

	/**
	 * 
	 * parse:(将JavaBean转换为XML文档，并输出). JavaBean无注解<br/>
	 * 
	 * @param declaredType
	 *            XML文档根节点Class
	 * @param value
	 *            待转换的JavaBean对象
	 * @param xmlFile
	 *            输出的xml文件
	 * @since 1.0
	 */
	public static <T> void marshal(Class<T> declaredType, T value, File xmlFile) {
		if (value == null) {
			return;
		}
		if (xmlFile == null) {
			return;
		}

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(declaredType);

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			JAXBElement<T> jaxbElement = new JAXBElement<T>(new QName(null,
					declaredType.getSimpleName()), declaredType, value);
			marshaller.marshal(jaxbElement, xmlFile);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 
	 * parse:(将JavaBean转换为XML文档). JavaBean有注解<br/>
	 * 
	 * @param declaredType
	 *            XML文档根节点Class
	 * @param value
	 *            待转换的JavaBean对象
	 * @return String 生成的XML文档内容
	 * @since 1.0
	 */
	public static <T> String marshalForAnnotation(Class<T> declaredType, T value) {
		if (value == null) {
			return StringUtils.EMPTY;
		}

		StringWriter out = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(declaredType);

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(value, out);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return out.toString();
	}

	/**
	 * 
	 * parse:(将JavaBean转换为XML文档). JavaBean有注解<br/>
	 * 
	 * @param declaredType
	 *            XML文档根节点Class
	 * @param value
	 *            待转换的JavaBean对象
	 * @param xmlFile
	 *            生成的XML文件
	 * @since 1.0
	 */
	public static <T> void marshalForAnnotation(Class<T> declaredType, T value,
                                                File xmlFile) {
		if (value == null) {
			return;
		}
		if (xmlFile == null) {
			return;
		}

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(declaredType);

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(value, xmlFile);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 
	 * Validatexml:(利用xsd文件验证xml的正确性). <br/>
	 * 
	 * @param xsdpath
	 *            schema文件
	 * @param xmlpath
	 *            xml文件
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 *             boolean
	 * @since 1.0
	 */
	public static boolean Validatexml(String xsdpath, String xmlpath)
			throws SAXException, IOException {
		boolean flag = false;
		// 建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		File schemaFile = FileUtils.findFile(xsdpath);
		// 利用schema工厂，接收验证文档文件对象生成Schema对象
		Schema schema = schemaFactory.newSchema(schemaFile);
		// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
		Validator validator = schema.newValidator();
		// 得到验证的数据源
		Source source = new StreamSource(xmlpath);
		// 开始验证，成功输出success!!!，失败输出fail
		try {
			validator.validate(source);
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 
	 * getJAXBContext:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @param clazz
	 * @return JAXBContext
	 * @since 1.0
	 */
	private static JAXBContext getJAXBContext(Class<?> clazz) {
		JAXBContext jaxbContext = JAXB_CONTEXT_CACHE.get(clazz);

		if (jaxbContext == null) {
			try {
				jaxbContext = JAXBContext.newInstance(clazz);
				JAXB_CONTEXT_CACHE.put(clazz, jaxbContext);
			} catch (JAXBException e) {
				throw new RuntimeException(e);
			}
		}

		return jaxbContext;
	}

	/**
	 * 
	 * isXML:(检查文本是否为合法格式的XML内容). <br/>
	 * 
	 * @param is
	 * @return
	 * @since 1.0
	 */
	public static boolean isXML(InputStream is) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			builder.parse(is);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * isXML:(检查文本是否为合法格式的XML内容). <br/>
	 * 
	 * @param str
	 * @return
	 * @since 1.0
	 */
	public static boolean isXML(String str) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			builder.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据注解生成XML文件，(将JavaBean转换为XML文档,忽略节点前的命名空间前缀). JavaBean有注解<br/>
	 * 
	 * @param declaredType
	 *            XML文档根节点Class
	 * @param value
	 *            待转换的JavaBean对象
	 * @param xmlFile
	 *            生成的XML文件
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @since 1.0
	 */
	public static <T> void marshalIgnoreNamespace(Class<T> declaredType, T value,
                                                  File xmlFile) throws JAXBException, FileNotFoundException,
            UnsupportedEncodingException {
		if (value == null) {
			return;
		}
		if (xmlFile == null) {
			return;
		}

		JAXBContext jaxbContext = JAXBContext.newInstance(declaredType);

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		FileOutputStream out = new FileOutputStream(xmlFile);
		OutputFormat format = new OutputFormat();
		format.setIndent(true);
		format.setNewlines(true);
		format.setNewLineAfterDeclaration(false);
		XMLWriter writer = new XMLWriter(out, format);
		XMLFilterImpl filter = new XMLFilterImpl() {

			private boolean ignoreNamespace = false;
			private String rooNamespace = null;
			private boolean isRootElement = true;

			@Override
			public void startDocument() throws SAXException {
				super.startDocument();
			}

			@Override
			public void startElement(String arg0, String arg1, String arg2,
                                     Attributes arg3) throws SAXException {
				if (this.ignoreNamespace) {
					arg0 = "";
				}
				if (this.isRootElement) {
					this.isRootElement = false;
				} else if (!"".equals(arg0) && !"xmlns".equals(arg1)) {
					arg1 = arg1 + " xmlns =\"" + arg0 + "\"";
				}
				super.startElement(arg0, arg1, arg1, arg3);
			}

			@Override
			public void endElement(String arg0, String arg1, String arg2)
					throws SAXException {
				if (this.ignoreNamespace) {
					arg0 = "";
				}
				super.endElement(arg0, arg1, arg1);
			}

			@Override
			public void startPrefixMapping(String arg0, String arg1)
					throws SAXException {
				if (this.rooNamespace != null) {
					arg1 = this.rooNamespace;
				}
				if (!this.ignoreNamespace) {
					super.startPrefixMapping("", arg1);
				}
			}
		};
		filter.setContentHandler(writer);
		marshaller.marshal(value, filter);
	}

	/**
	 * 将XML文件解析为JavaBean（忽略节点前的命名空间前缀）
	 * 
	 * @param xmlFile
	 *            XML文件
	 * @param clazz
	 *            XML文档根节点Class
	 * @return 解析结果
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws FileNotFoundException
	 */
	public static <T> T unmarshalIgnoreNamespace(String xmlFile, Class<T> clazz) throws JAXBException, SAXException, FileNotFoundException {
		if (xmlFile == null) {
			return null;
		}

		Object object = null;

		JAXBContext jaxbContext = getJAXBContext(clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		XMLReader reader = XMLReaderFactory.createXMLReader();
		XMLFilterImpl filter = new XMLFilterImpl() {
			private boolean ignoreNamespace = true;

			@Override
			public void startElement(String arg0, String arg1, String arg2,
                                     Attributes arg3) throws SAXException {
				if (this.ignoreNamespace) {
					super.startElement(arg0, arg1, arg2, arg3);
					ignoreNamespace = false;
				} else {
					super.startElement("", arg1, arg2, arg3);
				}
			}

		};
		filter.setParent(reader);
		InputSource input = new InputSource(new FileReader(xmlFile));
		SAXSource source = new SAXSource(filter, input);
		object = unmarshaller.unmarshal(source);

		return (T) object;
	}
}
