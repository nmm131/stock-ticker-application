package com.advanced.util;

import org.junit.Test;

import com.advanced.util.XMLUtils;
import com.advanced.xml.Family;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for XML utils.
 */
public class XMLUtilsTest {

    private static String FATHERS_NAME = "Fred";
    private static String MOTHERS__NAME = "Sally";

    private static String xmlInstance = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"+
            "<family>\n" +
            "    <father age=\"40\">" + FATHERS_NAME + "</father>\n" +
            "    <mother age=\"40\">" + MOTHERS__NAME + "</mother>\n" +
            "    <kids>\n" +
            "        <child age=\"11\" grade=\"5\">Peter</child>\n" +
            "        <child age=\"15\" grade=\"9\">Bill</child>\n" +
            "        <child age=\"09\" grade=\"3\">Sally</child>\n" +
            "    </kids>\n" +
            "</family>";


    private static String xmlStocks = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<stocks>\n" +
            "    <stock symbol=\"VNET\" price=\"110.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"AGTK\" price=\"120.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"AKAM\" price=\"3.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"AOL\"  price=\"30.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"BCOM\" price=\"10.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"BIDU\" price=\"10.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"BCOR\" price=\"12.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"WIFI\" price=\"16.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"BRNW\" price=\"0.70\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"CARB\" price=\"9.80\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"JRJC\" price=\"111.11\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"CCIH\" price=\"22.20\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"CHIC\" price=\"4.30\" time=\"2015-02-10 00:00:01\"/>F\n" +
            "    <stock symbol=\"CNV\"  price=\"13.43\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"CCOI\" price=\"1.10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"CNCG\" price=\".10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"CXDO\" price=\"90.00\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"CRWG\" price=\"52.99\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"ELNK\" price=\"45.40\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"EATR\" price=\"15.60\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"EDXC\" price=\"18.40\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"ENV\"  price=\"220.61\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"EPAZ\" price=\"101.11\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"FB\"   price=\"500.17\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"FDIT\" price=\"160.90\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"FLPC\" price=\"177.70\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"GCLT\" price=\"8.90\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"GOOG\" price=\"700.10\" time=\"2015-02-10 00:00:01\"/>L\n" +
            "    <stock symbol=\"GOOG\" price=\".10\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"GREZ\" price=\"77.91\" time=\"2015-02-10 00:00:01\"/>F\n" +
            "    <stock symbol=\"IACI\" price=\"40.52\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"ICOA\" price=\"48.30\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"IIJI\" price=\"32.80\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"ILIA\" price=\"188.22\" time=\"2015-02-10 00:00:01\"/>Y\n" +
            "    <stock symbol=\"INAP\" price=\"2.12\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"IPAS\" price=\"1.02\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"JCOM\" price=\"19.99\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"LOGL\" price=\"18.21\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"LLNW\" price=\"45.55\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"LOOK\" price=\"38.90\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"MEET\" price=\"21.27\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"MEET\" price=\"310.31\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"VOIS\" price=\"440.51\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"MOMO\" price=\"8.51\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"NETE\" price=\"13.16\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"NTES\" price=\"14.23\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"EGOV\" price=\"17.35\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"NQ\" price=\"110.77\" time=\"2015-02-10 00:00:01\"/>\n" +
            "    <stock symbol=\"OTOW\" price=\"60.41\" time=\"2015-02-10 00:00:01\"/>\n" +
            "</stocks>";

    @Test
    public void testUnmarshall() throws Exception {
        Family family = XMLUtils.unmarshall(xmlInstance, Family.class);
        validateFamily(family);
    }

    @Test
    public void testUnmarshallWithSchemaValidation()throws Exception {
        Family family = XMLUtils.unmarshall(xmlInstance, Family.class, "/xml/family.xsd");
        validateFamily(family);
    }

    @Test
    public void testMarshall() throws Exception {
        Family family = XMLUtils.unmarshall(xmlInstance, Family.class, "/xml/family.xsd");
        String xml = XMLUtils.marshall(family);
        // input xml should be the same as output xml
        assertEquals("XML out is correct", xml.trim() ,xmlInstance.trim());
    }

    private void validateFamily(Family family) {
        assertTrue("Father name is correct", family.getFather().getContent().equals(FATHERS_NAME));
        assertTrue("Mother name is correct", family.getMother().getContent().equals(MOTHERS__NAME));
        assertTrue("There are three kids", family.getKids().getChild().size() == 3);
    }


}
