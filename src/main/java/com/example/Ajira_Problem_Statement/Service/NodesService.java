package com.example.Ajira_Problem_Statement.Service;

import com.example.Ajira_Problem_Statement.Enumeration.DeviceType;
import com.example.Ajira_Problem_Statement.Model.Nodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NodesService {
    String result="Error: Invalid command syntax.";
    String nodeName="";
    String nodeType="";
    int deviceStrength;
    private List<Nodes> nodesList = new ArrayList<>();

    public boolean isNodeParameterLengthValid(String[] nodeParameter)
    {
        if(nodeParameter.length<3||nodeParameter.length>3)
            return true;
        return false;
    }

    public boolean isNodeAlreadyExist(String nodeName)
    {
        for(Nodes node:nodesList)
        {
            if(node.getName().equals(nodeName))
            {
                return true;
            }
        }
        return false;
    }

    public void createNodeConnection(String nodeName1,String nodeName2)
    {
        for(Nodes node:nodesList)
        {
            if(node.getName().equals(nodeName1))
            {
                List<String> nodeConnection=node.getNodeConnections();
                nodeConnection.add(nodeName2);
                node.setNodeConnections(nodeConnection);
                return;
            }
        }
    }

    public boolean isNodesAlreadyConnected(String nodeName1,String nodeName2)
    {
        List<String> nodeConnection=new ArrayList<>();
        for(Nodes node:nodesList) {
            if (node.getName().equals(nodeName1)) {
                nodeConnection = node.getNodeConnections();
                break;
            }
        }
        for(String node:nodeConnection)
        {
            if(node.equals(nodeName2))
                return true;
        }
        return false;
    }

    public void dfs(String source,String destination,Set<String> visited,ArrayList<String> path,int messageStrength)
    {
        if(source.equals(destination))
            return;
        visited.add(source);
        String type="";
        List<String> nodeConnection=new ArrayList<>();
        for(Nodes node:nodesList)
         {
            if (node.getName().equals(source))
            {
                nodeConnection = node.getNodeConnections();
                type=node.getType();
                if(type.equals("COMPUTER"))
                    messageStrength--;
                else
                    messageStrength*=2;
                break;
            }
        }
        for(String node:nodeConnection) {
            path.add(source);
            if (messageStrength > 0 && !path.contains(node))
                dfs(node, destination, visited, path, messageStrength);
            path.remove(source);
            if (type.equals("COMPUTER"))
                messageStrength++;
            else
                messageStrength /= 2;
        }
        visited.remove(source);
    }

    public String createNode(String[] nodeParameter)
    {
        result="Error: Invalid command syntax.";
        Nodes node=new Nodes();
        if(isNodeParameterLengthValid(nodeParameter))
            return result;
        nodeName=nodeParameter[2];
        nodeType=nodeParameter[1];
        if(((!nodeType.equals("COMPUTER"))&&(!nodeType.equals("REPEATER"))))
            return result;
        for(Nodes nodes:nodesList)
        {
            if(nodes.getName().equals(nodeName))
            {
                result="Error: That name already exists.";
                return result;
            }
        }
        if(nodeType.equals(DeviceType.COMPUTER.name())) {
            node = new Nodes(nodeName, DeviceType.COMPUTER.name(), 5, new ArrayList<>());
        }
        else {
            node = new Nodes(nodeName, DeviceType.REPEATER.name(), 0, new ArrayList<>());
        }
        nodesList.add(node);
        result="Successfully added "+nodeName+".";
        return result;
    }
    public  String updateDeviceStrength(String[] nodeParameter)
    {
        result="Error: Invalid command syntax.";
        if(isNodeParameterLengthValid(nodeParameter))
            return result;
        nodeName=nodeParameter[1];
        if(nodeName.charAt(0)=='R')
            return result;
        try
        {
            deviceStrength = Integer.parseInt(nodeParameter[2]);
        }
        catch(NumberFormatException e) {
            return result;
        }
        for(Nodes node:nodesList)
        {
            if(node.getName().equals(nodeName))
            {
                node.setDeviceStrength(deviceStrength);
                result="Successfully defined strength.";
                break;
            }
        }
        return result;
    }

    public  String createConnection(String[] nodeParameter)
    {
        result="Error: Invalid command syntax.";
        if(isNodeParameterLengthValid(nodeParameter))
            return result;
        String nodeName1=nodeParameter[1];
        String nodeName2=nodeParameter[2];
        if(nodeName1.equals(nodeName2))
            return "Error: Cannot connect device to itself.";
        if(isNodeAlreadyExist(nodeName1)&&isNodeAlreadyExist(nodeName2))
        {
            if(isNodesAlreadyConnected(nodeName1,nodeName2))
                return "Error: Devices are already connected.";
            else
            {
                createNodeConnection(nodeName1, nodeName2);
                createNodeConnection(nodeName2, nodeName1);
                return "Successfully connected.";
            }
        }
        return "Error: Node not found.";
    }

    public String getRouteInfo(String[] nodeParameter)
    {
        result="Error: Invalid command syntax.";
        if(isNodeParameterLengthValid(nodeParameter))
            return result;
        String nodeName1=nodeParameter[1];
        String nodeName2=nodeParameter[2];
        /* Path not possible for repeaters */
        if(nodeName1.charAt(0)=='R'||nodeName2.charAt(0)=='R')
              return "Error: Route cannot be calculated with a repeater.";
        if(isNodeAlreadyExist(nodeName1)&&isNodeAlreadyExist(nodeName2))
        {
                        if(nodeName1.equals(nodeName2))
                                return nodeName1+"->"+nodeName2;
                        int messageStrength=0;
                        for(Nodes nodes:nodesList)
                        {
                            if(nodes.getName().equals(nodeName1))
                            {
                                messageStrength=nodes.getDeviceStrength();
                            }
                        }

                        Set<String> visited=new HashSet<String>();
                        ArrayList<String> path=new ArrayList<String>();
                        path.add(nodeName1);
                        /* DFS to get route path from Source to Destination */
                        dfs(nodeName1,nodeName2,visited,path,messageStrength);

                        StringBuilder finalPath=new StringBuilder();

                        if(path.size()==1)
                            return "Error: Route not found!";
                        else {
                            for (int i=0;i<path.size();i++) {
                                finalPath.append(path.get(i));
                                if(i!=(path.size()-1))
                                  finalPath.append("->");
                            }
                            /* path Found */
                            return finalPath.toString();
                        }
        }
        return "Error: Node not found.";
    }
}
