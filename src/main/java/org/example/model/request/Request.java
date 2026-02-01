package org.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.model.Operations;

import java.io.Serializable;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    Operations operations;
    String data;
}
