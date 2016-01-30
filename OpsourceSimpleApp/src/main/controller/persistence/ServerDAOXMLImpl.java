package controller.persistence;

import model.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ivan on 19/1/16.
 */
public class ServerDAOXMLImpl implements ServerDAO {

    @XmlRootElement(name = "server", namespace="http://www.opsource.net/simpleapp")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ServerXML{
        @XmlElement(name = "id", required = true, namespace = "http://www.opsource.net/simpleapp")
        protected String id;
        @XmlElement(name = "name", required = true, namespace = "http://www.opsource.net/simpleapp")
        protected String name;
    }

    private String nameResource;

    public String getNameResource(){
        return nameResource;
    }

    public void setNameResource(String newName){
        this.nameResource = newName;
    }

    /**
     * It locates the XML file in the filesystem and gets ready to receive orders
     * @param nameResource The path of the xml file with the servers description
     */
    public ServerDAOXMLImpl(String nameResource){
        this.nameResource = nameResource;
    }

    public void closeConnection(){ }

    public List<Server> listAllServers() {
        List<Server> listServers = new LinkedList<>();
        try {
            File xmlFile = new File(nameResource);
            JAXBContext jaxbContext = JAXBContext.newInstance(ServerXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ServerXML server = (ServerXML) jaxbUnmarshaller.unmarshal(xmlFile);
            listServers.add(new Server(Integer.parseInt(server.id), server.name));
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return listServers;
    }


    public boolean removeServer(int id) {
        throw new UnsupportedOperationException("The operation 'Remove server' is not available yet in XML");
    }


    public boolean editServer(int id, String newName) {
        throw new UnsupportedOperationException("The operation 'Edit server' is not available yet in XML");
    }

    public boolean storeServer(Server srv) {
        throw new UnsupportedOperationException("The operation 'Store server' is not available yet in XML");
    }
}
