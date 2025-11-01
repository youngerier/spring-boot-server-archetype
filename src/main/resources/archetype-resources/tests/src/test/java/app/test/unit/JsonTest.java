#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.test.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


@Slf4j
public class JsonTest {
    @Test
    public void testDeserialize() throws JsonProcessingException {


        String json = "{${symbol_escape}"username${symbol_escape}":${symbol_escape}"testa${symbol_escape}",${symbol_escape}"password${symbol_escape}":${symbol_escape}"test${symbol_escape}"}";

        RbacUserDTO rbacUserDTO = new ObjectMapper().readValue(json, RbacUserDTO.class);

        System.out.println(rbacUserDTO);

    }

    @Test
    //Parsing using the Auto-Detect Parser
    public void testParseExample() throws IOException, SAXException, TikaException {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        try (InputStream stream = JsonTest.class.getClassLoader().getResourceAsStream("d.png")) {
            parser.parse(stream, handler, metadata);
            System.out.println(handler.toString());
            ;
        }
    }

    @Test
    //Parsing using the Auto-Detect Parser
    public void testParseExampl11e() throws IOException, SAXException, TikaException {
        Tika tika = new Tika();
        try (InputStream stream = JsonTest.class.getClassLoader().getResourceAsStream("card.zip")) {
            Metadata metadata = new Metadata();
            String fileType = tika.detect(stream, metadata);
            MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
            MimeType mimeType = mimeTypes.forName(fileType);
            String fileExtension = mimeType.getExtension();
            System.out.println(fileType);
        }
    }

    @Test
    public void testBuildContext() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        log.info("beanDefinitionNames: {}", Arrays.toString(beanDefinitionNames));
    }

    @Data
    static class RbacUserDTO {
        @NotNull
        private String username;

        private String password;
    }
}
