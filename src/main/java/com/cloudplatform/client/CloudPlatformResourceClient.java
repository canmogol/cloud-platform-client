package com.cloudplatform.client;


import com.cloudplatform.app.dto.*;
import com.cloudplatform.app.restful.AppResource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.UUID;

/**
 * blocking server and blocking client request response example with multiple models/entities
 */
public class CloudPlatformResourceClient implements Runnable {

    private Log log = LogFactory.getLog(CloudPlatformResourceClient.class);

    private final String url;

    public CloudPlatformResourceClient(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        log.info(">>> " + getClass().getSimpleName() + " BEGIN");

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);

        // get a resource to call
        AppResource resource = target.proxy(AppResource.class);

        // CREATE REQUEST DTO
        SystemInfoRequestDTO requestDTO = new SystemInfoRequestDTO();
        // set a UUID for this request
        requestDTO.setRequestUUID(UUID.randomUUID().toString());
        // collect system status
        StatusDTO statusDTO = new StatusDTO();
        // set device id
        statusDTO.setDeviceId(111);
        // disk status
        DiskStatusDTO diskStatusDTO = new DiskStatusDTO();
        diskStatusDTO.setTotalRootDisk("112Gi");
        diskStatusDTO.setUsedRootDisk("94Gi");
        diskStatusDTO.setFreeRootDisk("18Gi");
        statusDTO.setDiskStatusDTO(diskStatusDTO);
        // memory status
        MemoryStatusDTO memoryStatusDTO = new MemoryStatusDTO();
        memoryStatusDTO.setTotalMemory("4000M");
        memoryStatusDTO.setUsedMemory("3000M");
        memoryStatusDTO.setFreeMemory("1000M");
        statusDTO.setMemoryStatusDTO(memoryStatusDTO);
        // set status object to request dto
        requestDTO.setStatusDTO(statusDTO);

        // CALL RESTFUL WEB SERVICE
        SystemInfoResponseDTO responseDTO = resource.statusChanged(requestDTO);

        // RESPONSE DTO CONTENTS
        log.info(ToStringBuilder.reflectionToString(responseDTO, ToStringStyle.MULTI_LINE_STYLE));
        log.info(ToStringBuilder.reflectionToString(responseDTO.getLatestVersionDTO(), ToStringStyle.MULTI_LINE_STYLE));
        log.info(ToStringBuilder.reflectionToString(responseDTO.getStatusResponseDTO(), ToStringStyle.MULTI_LINE_STYLE));

        log.info("<<< " + getClass().getSimpleName() + " END");
    }
}
