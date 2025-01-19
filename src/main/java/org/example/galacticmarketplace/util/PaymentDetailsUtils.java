package org.example.galacticmarketplace.util;

import org.example.galacticmarketplace.web.exception.ParamsViolationDetails;
import lombok.experimental.UtilityClass;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@UtilityClass
public class PaymentDetailsUtils {

    public static ProblemDetail getValidationErrorsProblemDetail(List<ParamsViolationDetails> validationResponse) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Request validation failed");
        problemDetail.setType(URI.create("uri:problem-type:validation-error"));
        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setProperty("invalidParams", validationResponse);
        return problemDetail;
    }
}