package mx.sjahl.avengers.service.dto;

import java.util.HashMap;

public class ColaboratorResponseDTO extends HashMap {


    public void setLastSync(String lastSync) {
        put("last_sync", lastSync);
    }

}
