package io.milton.http.report;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 *
 * @author brad
 */
public class ReportUtils {

	/**
	 * find the first element with the given name
	 *
	 * @param root
	 * @param name
	 * @param ns
	 * @return
	 */
	public static Element find(Element root, String name, Namespace ns) {
		for (Object child : root.getChildren()) {
			if (child instanceof Element) {
				Element elChild = (Element) child;
				if (elChild.getName().equals(name)) {
					if (ns == null || ns.getURI().equals(elChild.getNamespaceURI())) {
						return elChild;
					}
				}
			}
		}
		for (Object child : root.getChildren()) {
			if (child instanceof Element) {
				Element elChild = (Element) child;
				Element found = find(elChild, name, ns);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	public static List<Element> findAll(Element root, String name, Namespace ns) {
		List<Element> list = new ArrayList<Element>();
		_findAll(root, name, ns, list);
		return list;
	}

	private static void _findAll(Element root, String name, Namespace ns, List<Element> list) {
		for (Object child : root.getChildren()) {
			if (child instanceof Element) {
				Element elChild = (Element) child;
				if (elChild.getName().equals(name)) {
					if (ns == null || ns.getURI().equals(elChild.getNamespaceURI())) {
						list.add(elChild);
					}
				}
			}
		}
		for (Object child : root.getChildren()) {
			if (child instanceof Element) {
				Element elChild = (Element) child;
				_findAll(elChild, name, ns, list);

			}
		}
	}

	public static Set<QName> getProps(Document doc, Namespace propNs) {
		Element elProp = doc.getRootElement().getChild("prop", propNs);
		if (elProp == null) {
			throw new RuntimeException("No prop element");
		}

		Set<QName> set = new HashSet<QName>();
		for (Object o : elProp.getChildren()) {
			if (o instanceof Element) {
				Element el = (Element) o;
				String local = el.getName();
				String ns = el.getNamespaceURI();
				set.add(new QName(ns, local, el.getNamespacePrefix()));
			}
		}
		return set;
	}
}