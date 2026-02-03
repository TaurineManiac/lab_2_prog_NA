package org.example.model.request;

import lombok.*;
import org.example.model.Operations;

import java.io.Serializable;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    Operations operations;
    String data;
    String password;

    public Request(Operations operations, String data) {
        this.operations = operations;
        this.data = data;
    }
}
