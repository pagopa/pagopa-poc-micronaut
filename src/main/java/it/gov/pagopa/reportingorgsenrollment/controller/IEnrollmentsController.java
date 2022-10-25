package it.gov.pagopa.reportingorgsenrollment.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gov.pagopa.reportingorgsenrollment.model.ProblemJson;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Tag(name = "Reporting Organizations Enrollments API")
@Validated
public interface IEnrollmentsController {

    @Operation(summary = "The organization creates a creditor institution.", security = {@SecurityRequirement(name = "ApiKey")}, operationId = "createOrganization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request created.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(name = "OrganizationModelResponse", implementation = OrganizationModelResponse.class))),
            @ApiResponse(responseCode = "401", description = "Wrong or missing function key.", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "The organization to create already exists.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "500", description = "Service unavailable.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class)))})
    @Post(value = "/organizations/{organizationFiscalCode}",
            produces = MediaType.APPLICATION_JSON)
    HttpResponse<OrganizationModelResponse> createOrganization(
            @Parameter(description = "The fiscal code of the Organization.", required = true)
            @NotBlank @PathVariable String organizationFiscalCode);
    
    
    @Operation(summary = "The organization deletes the creditor institution.", security = {@SecurityRequirement(name = "ApiKey")}, operationId = "removeOrganization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request deleted.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(name = "StringResponse", implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Wrong or missing function key.", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not found the creditor institution.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "500", description = "Service unavailable.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class)))})
    @Delete(value = "/organizations/{organizationFiscalCode}",
            produces = MediaType.APPLICATION_JSON)
    HttpResponse<String> removeOrganization(
    		@Parameter(description = "The fiscal code of the Organization.", required = true)
            @NotBlank @PathVariable String organizationFiscalCode);
    
    @Operation(summary = "The organization get the single enrollment for the creditor institution.", security = {@SecurityRequirement(name = "ApiKey")}, operationId = "getOrganization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtained single enrollment.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(name = "OrganizationModelResponse", implementation = OrganizationModelResponse.class))),
            @ApiResponse(responseCode = "401", description = "Wrong or missing function key.", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not found the enroll service.", content = @Content(schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "500", description = "Service unavailable.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class)))})
    @Get(value = "/organizations/{organizationFiscalCode}",
            produces = MediaType.APPLICATION_JSON)
    HttpResponse<OrganizationModelResponse> getOrganization(
    		@Parameter(description = "The fiscal code of the Organization.", required = true)
            @NotBlank @PathVariable String organizationFiscalCode);
    
    @Operation(summary = "The organization get all enrollments of creditor institutions.", security = {@SecurityRequirement(name = "ApiKey")}, operationId = "getOrganizations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtained all enrollments.", content = @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(name = "ListOfOrganizationModelResponse", implementation = OrganizationModelResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Wrong or missing function key.", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class)))})
    @Get(value = "/organizations",
            produces = MediaType.APPLICATION_JSON)
    HttpResponse<List<OrganizationModelResponse>> getOrganizations();
}
