package com.advanced.app;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.advanced.util.StockServiceException;
import com.advanced.util.StockServiceFactory;
import com.advanced.xml.Stocks;
import com.advanced.xml.Stocks.StockQuote;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A basic app that shows how to marshall and unmarshal XML instances.
 */
public class StockQuoteJAXB {

    private static String xmlInstance ="<stocks>\n" +
    		"	 <stockQuote symbol=\"VNET\" price=\"110.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"AGTK\" price=\"120.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"AKAM\" price=\"3.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"AOL\"  price=\"30.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"BCOM\" price=\"10.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"BIDU\" price=\"10.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"BCOR\" price=\"12.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"WIFI\" price=\"16.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"BRNW\" price=\"0.70\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CARB\" price=\"9.80\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"JRJC\" price=\"111.11\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CCIH\" price=\"22.20\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CHIC\" price=\"4.30\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CNV\"  price=\"13.43\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CCOI\" price=\"1.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CNCG\" price=\".10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CXDO\" price=\"90.00\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"CRWG\" price=\"52.99\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"ELNK\" price=\"45.40\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"EATR\" price=\"15.60\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"EDXC\" price=\"18.40\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"ENV\"  price=\"220.61\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"EPAZ\" price=\"101.11\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"FB\"   price=\"500.17\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"FDIT\" price=\"160.90\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"FLPC\" price=\"177.70\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"GCLT\" price=\"8.90\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"GOOG\" price=\"700.10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"GOOG\" price=\".10\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"GREZ\" price=\"77.91\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"IACI\" price=\"40.52\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"ICOA\" price=\"48.30\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"IIJI\" price=\"32.80\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"ILIA\" price=\"188.22\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"INAP\" price=\"2.12\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"IPAS\" price=\"1.02\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"JCOM\" price=\"19.99\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"LOGL\" price=\"18.21\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"LLNW\" price=\"45.55\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"LOOK\" price=\"38.90\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"MEET\" price=\"21.27\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"MEET\" price=\"310.31\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"VOIS\" price=\"440.51\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"MOMO\" price=\"8.51\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"NETE\" price=\"13.16\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"NTES\" price=\"14.23\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"EGOV\" price=\"17.35\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"NQ\" price=\"110.77\" time=\"2015-02-10 00:00:01\"/>\n" + 
            "    <stockQuote symbol=\"OTOW\" price=\"60.41\" time=\"2015-02-10 00:00:01\"/>" +
            "</stocks>";


    public static void main(String[] args) throws JAXBException, StockServiceException {

        // here is how to go from XML to Java
        JAXBContext jaxbContext = JAXBContext.newInstance(Stocks.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Stocks stocks = (Stocks) unmarshaller.unmarshal(new StringReader(xmlInstance));
        System.out.println(stocks.toString());

        // here is how to go from Java to XML
        JAXBContext context = JAXBContext.newInstance(Stocks.class);
        Marshaller marshaller = context.createMarshaller();
        //for pretty-print XML in JAXB
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(stocks, System.out);
        
        for (int i = 0; i < stocks.getStockQuote().size(); i++) {
        	String symbol = stocks.getStockQuote().get(i).getSymbol();
        	double price = stocks.getStockQuote().get(i).getPrice();
        	Calendar time = stocks.getStockQuote().get(i).getTime();
        	
            StockServiceFactory.getStockService().addStocksToDatabase(symbol, price, time);
        }

    }
}
