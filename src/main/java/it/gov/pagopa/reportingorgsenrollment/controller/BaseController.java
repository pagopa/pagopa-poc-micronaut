package it.gov.pagopa.reportingorgsenrollment.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.gov.pagopa.reportingorgsenrollment.model.AppInfo;
import it.gov.pagopa.reportingorgsenrollment.model.AppMetrics;
import it.gov.pagopa.reportingorgsenrollment.model.ProblemJson;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@ExecuteOn(TaskExecutors.IO)
@Controller()
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Value("${info.application.artifactId}")
    private String name;

    @Value("${info.application.version}")
    private String version;

    @Value("${info.properties.environment}")
    private String environment;

    @Value("${time.mean.default}")
    private int defaultMeanTime;

    private MeterRegistry meterRegistry;
    private Instant referenceTime = Instant.now();

    @Inject
    public BaseController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Hidden
    @Get
    public HttpResponse<?> base(){
        return HttpResponse.seeOther(UriBuilder.of("/swagger-ui/").build());
    }

    @Operation(summary = "health check", description = "Return OK if application is started", security = {@SecurityRequirement(name = "ApiKey")}, tags = {"Base"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AppInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProblemJson.class)))})
    @Get(value = "/info")
    public HttpResponse<AppInfo> healthCheck() {
        // Used just for health checking
        AppInfo info = AppInfo.builder()
                .name(name)
                .version(version)
                .environment(environment)
                .build();
        return HttpResponse.status(HttpStatus.OK).body(info);
    }

    @Get("/metrics/custom")
    @Timed
    public HttpResponse<AppMetrics> getMeanTimePerRequest() {
        int meanTimePerRequest;

        Timer timer = this.meterRegistry
                              .find("http.server.requests")
                              .timer();

        double mean = timer.mean(TimeUnit.MILLISECONDS);
        LOGGER.info("metrics/http.web.request.mean called, value: " + mean);

        if(timer == null)
            meanTimePerRequest = defaultMeanTime;
        else meanTimePerRequest = (int) mean;

        boolean isTimeElapsed = Duration.between(referenceTime, Instant.now()).toSeconds() >= 10;
        if(isTimeElapsed) {
            this.meterRegistry.clear();
            referenceTime = Instant.now();
        }

        AppMetrics metrics = AppMetrics.builder()
                                .meanTimePerRequest(meanTimePerRequest)
                                .build();
        return HttpResponse.status(HttpStatus.OK).body(metrics);
    }
}
