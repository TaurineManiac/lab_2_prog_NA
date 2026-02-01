package org.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    Boolean isOperationCompleted;
    String description;
    String data;
}
