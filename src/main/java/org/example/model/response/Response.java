package org.example.model.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    Boolean isOperationCompleted;
    String description;
    String data;
}
