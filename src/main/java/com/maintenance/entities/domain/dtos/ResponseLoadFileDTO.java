package com.maintenance.entities.domain.dtos;

import com.maintenance.entities.domain.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLoadFileDTO {

    private List<Client> clients_saved;
    private List<Client> clients_error;

}
