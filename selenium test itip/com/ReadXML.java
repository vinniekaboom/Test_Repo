package network.ite.run.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory; 

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXML {  

	public ArrayList<String> readXML(String appname) { 
			
		ArrayList<String> userinfo  = new ArrayList<String>();
		
        try {
            FileInputStream file = new FileInputStream(new File("C:\\testdaily\\xml\\users.xml"));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(file);
            XPath xPath =  XPathFactory.newInstance().newXPath();      	
 
            String expression = "/daily/app[@id='" + appname + "']";
            
            Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
            if(null != node) {
            	NodeList nodeList = node.getChildNodes();
            	for (int i = 0;null!=nodeList && i < nodeList.getLength(); i++) {
            		Node nod = nodeList.item(i);
					if(nod.getNodeType() == Node.ELEMENT_NODE)
						userinfo.add(nod.getFirstChild().getNodeValue());        					
            	}
            }
                                 
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (SAXException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (ParserConfigurationException e) {
        	e.printStackTrace();
        } catch (XPathExpressionException e) {
        	e.printStackTrace();
        }
		return userinfo;

      }
	}
	