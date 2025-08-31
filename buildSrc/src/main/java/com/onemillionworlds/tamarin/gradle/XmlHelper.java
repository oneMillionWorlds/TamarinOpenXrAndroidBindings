package com.onemillionworlds.tamarin.gradle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Helper class for traversing XML documents using string constants.
 * This class provides methods for getting elements, attributes, and navigating the XML structure.
 */
public class XmlHelper {

    /**
     * Gets all child elements with the given tag name from the parent element.
     *
     * @param parent The parent element
     * @param tagName The tag name to search for
     * @return A list of matching elements
     */
    public static List<Element> getElements(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elements.add((Element) node);
            }
        }
        return elements;
    }

    /**
     * Gets the first child element with the given tag name from the parent element.
     *
     * @param parent The parent element
     * @param tagName The tag name to search for
     * @return An Optional containing the element if found, or empty if not found
     */
    public static Optional<Element> getElement(Element parent, String tagName) {
        List<Element> elements = getElements(parent, tagName);
        return elements.isEmpty() ? Optional.empty() : Optional.of(elements.get(0));
    }

    /**
     * Gets the value of an attribute from an element.
     *
     * @param element The element
     * @param attributeName The attribute name
     * @return An Optional containing the attribute value if found, or empty if not found
     */
    public static Optional<String> getAttribute(Element element, String attributeName) {
        if (element.hasAttribute(attributeName)) {
            return Optional.of(element.getAttribute(attributeName));
        }
        return Optional.empty();
    }

    /**
     * Gets the text content of an element.
     *
     * @param element The element
     * @return The text content of the element
     */
    public static String getTextContent(Element element) {
        return element.getTextContent();
    }

    /**
     * Gets all direct child elements of the parent element.
     *
     * @param parent The parent element
     * @return A list of all direct child elements
     */
    public static List<Element> getChildElements(Element parent) {
        NodeList childNodes = parent.getChildNodes();
        List<Element> childElements = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                childElements.add((Element) node);
            }
        }
        return childElements;
    }

    /**
     * Gets all direct child elements of the parent element with the given tag name.
     *
     * @param parent The parent element
     * @param tagName The tag name to search for
     * @return A list of matching direct child elements
     */
    public static List<Element> getDirectChildElements(Element parent, String tagName) {
        List<Element> childElements = getChildElements(parent);
        return childElements.stream()
                .filter(element -> element.getTagName().equals(tagName))
                .toList();
    }
}