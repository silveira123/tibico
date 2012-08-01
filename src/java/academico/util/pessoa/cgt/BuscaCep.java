package academico.util.pessoa.cgt;

import academico.util.pessoa.cdp.Webservicecep;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class BuscaCep {

	public static Webservicecep getEndereco(String cep) throws JAXBException, MalformedURLException{
		
		  JAXBContext jc = JAXBContext.newInstance(Webservicecep.class);
		
	       Unmarshaller u = jc.createUnmarshaller();
	       URL url = new URL( "http://cep.republicavirtual.com.br/web_cep.php?cep="+cep+"&formato=xml" );
	       Webservicecep wCep = (Webservicecep) u.unmarshal( url );
	       
	       return wCep;
		
	}

}
