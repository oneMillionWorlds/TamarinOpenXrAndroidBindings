package com.onemillionworlds.tamarin.gradle.tasks.parsers;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlFeatureParserTest {

    @Test
    void parseCoreCommands() throws Exception {
        // Only commands required by a <feature> (a core version) are core. Commands only required by an <extension>
        // (including a promoted extension's alias of a core command) are not
        String xml = """
                <registry>
                    <feature api="openxr" name="XR_VERSION_1_0" number="1.0">
                        <require>
                            <command name="xrCreateInstance"/>
                            <command             name="xrStringToPath"/>
                        </require>
                    </feature>
                    <feature api="openxr" name="XR_VERSION_1_1" number="1.1">
                        <require>
                            <command name="xrLocateSpaces"/>
                        </require>
                    </feature>
                    <extensions>
                        <extension name="XR_KHR_locate_spaces">
                            <require>
                                <command name="xrLocateSpacesKHR"/>
                            </require>
                        </extension>
                        <extension name="XR_BD_body_tracking">
                            <require>
                                <command name="xrCreateBodyTrackerBD"/>
                            </require>
                        </extension>
                    </extensions>
                </registry>
                """;
        Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)))
                .getDocumentElement();

        assertEquals(Set.of("xrCreateInstance", "xrStringToPath", "xrLocateSpaces"), XmlFeatureParser.parseCoreCommands(root));
    }
}
