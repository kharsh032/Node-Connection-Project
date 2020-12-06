package com.example.Ajira_Problem_Statement.Controller;

import com.example.Ajira_Problem_Statement.Service.NodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ajiranet/process")
public class NodesController {

    @Autowired
    NodesService nodesService;
    @RequestMapping(method= RequestMethod.GET,value="",consumes = {"application/json"})
    public String nodesCommand(@RequestBody String parameter)
    {
        String result="";
        String[] nodeParameter = parameter.split("\\s+");
        if(nodeParameter.length<=1)
            return "Error: Invalid command syntax.";
        if(nodeParameter[0].equals("ADD"))
        {
            result=nodesService.createNode(nodeParameter);
        }
        else if(nodeParameter[0].equals("SET_DEVICE_STRENGTH"))
        {
            result=nodesService.updateDeviceStrength(nodeParameter);
        }
        else if(nodeParameter[0].equals("CONNECT"))
        {
            result=nodesService.createConnection(nodeParameter);
        }
        else if(nodeParameter[0].equals("INFO_ROUTE"))
        {
            result=nodesService.getRouteInfo(nodeParameter);
        }
        return result;
    }
}

