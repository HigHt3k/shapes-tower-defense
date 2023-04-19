package org.game.profile;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.util.JAXBSource;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@XmlRootElement(name = "profile")
public class Profile {

    private String name;
    private int xp;

    public Profile() {

    }

    public Profile(String name, int xp) {
        this.name = name;
        this.xp = xp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public static Profile loadProfileFromXml(String filename, String name) throws Exception {
        File file = new File(filename);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filename);
        }

        validateXml(file);

        JAXBContext context = JAXBContext.newInstance(ProfileList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProfileList profileList = (ProfileList) unmarshaller.unmarshal(file);

        for (Profile profile : profileList.getProfiles()) {
            if (profile.getName().equals(name)) {
                return profile;
            }
        }

        return null;
    }

    public static void appendProfileToXml(Profile profile) throws JAXBException, FileNotFoundException, SAXException, IOException {
        File file = new File("profile.xml");
        if (!file.exists()) {
            ProfileList profileList = new ProfileList();
            profileList.getProfiles().add(profile);
            saveProfileListToXml(profileList, "profile.xml");
            return;
        }

        validateXml(file);

        JAXBContext jaxbContext = JAXBContext.newInstance(ProfileList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ProfileList profileList = (ProfileList) unmarshaller.unmarshal(file);

        boolean profileExists = false;
        for (Profile p : profileList.getProfiles()) {
            if (p.getName().equals(profile.getName())) {
                p.setXp(profile.getXp());
                profileExists = true;
                break;
            }
        }
        if (!profileExists) {
            profileList.getProfiles().add(profile);
        }

        saveProfileListToXml(profileList, "profile.xml");
    }

    private static void saveProfileListToXml(ProfileList profileList, String filename) throws JAXBException, FileNotFoundException {
        File file = new File(filename);
        JAXBContext context = JAXBContext.newInstance(ProfileList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(profileList, file);
    }

    public static void validateXml(File xmlFile) throws SAXException, IOException, JAXBException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("profiles.xsd"));

        JAXBContext jaxbContext = JAXBContext.newInstance(ProfileList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(event -> {
            if (event.getSeverity() == ValidationEvent.WARNING) {
                System.out.println("Validation warning: " + event.getMessage());
                return true;
            } else if (event.getSeverity() == ValidationEvent.ERROR) {
                System.out.println("Validation error: " + event.getMessage());
                return false;
            } else if (event.getSeverity() == ValidationEvent.FATAL_ERROR) {
                System.out.println("Validation fatal error: " + event.getMessage());
                return false;
            }
            return true;
        });
        ProfileList profileList = (ProfileList) unmarshaller.unmarshal(xmlFile);
        Validator validator = schema.newValidator();
        validator.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("Validation warning: " + exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println("Validation error: " + exception.getMessage());
                throw exception;
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("Validation fatal error: " + exception.getMessage());
                throw exception;
            }
        });
        validator.validate(new JAXBSource(jaxbContext, profileList));
    }
}