package com.project.demo.documentation;


import com.project.demo.dto.ProjectDto;
import com.project.demo.dto.TaskGetDto;
import com.project.demo.dto.TaskPostDto;
import com.project.demo.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.project.demo.util.Contants.HttpCodes.*;
import static com.project.demo.util.Contants.HttpCodes.STATUS_FORBIDDEN;
import static com.project.demo.util.Contants.TaskApi.*;

@Tag(name = TAG_NAME, description = TAG_DESCRIPTION)
public interface ITaskController {

    @Operation(summary = SUMARY_GET_ID, description = DESCRIPTION_GET_ID)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation =TaskGetDto.class))}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_TASK, content = {@Content}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<TaskGetDto> getTaskById(@Parameter(name = PARAMETER_ID) @PathVariable Long id);

    @Operation(summary = SUMARY_PAGINATION, description = DESCRIPTION_PAGINATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<Map<String,Object>> pageTasks(@Parameter(name = PARAMETER_TASK_PAGE) @RequestParam Integer numberPage, @Parameter(name = PARAMETER_ID) @RequestParam Long id, Pageable pageable);

    @Operation(summary = SUMARY_PAGINATION, description = DESCRIPTION_PAGINATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<Map<String,Object>> pageTasksByCondition(@Parameter(name = PARAMETER_TASK_PAGE) @RequestParam Integer numberPage, @Parameter(name = PARAMETER_ID) @RequestParam Long id, Pageable pageable);

    @Operation(summary = SUMARY_ADD, description = DESCRIPTION_ADD)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_CREATED, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = TaskGetDto.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content}),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = ERROR_SERVER, content = {@Content})
    })
    ResponseEntity<TaskGetDto> createTask(@Parameter(name = PARAMETER_TASK_ADD) @RequestBody @Valid TaskPostDto dto, @AuthenticationPrincipal User loggedUser);

    @Operation(summary = SUMARY_UPDATE, description = DESCRIPTION_UPDATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = TaskGetDto.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_TASK, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content}),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = ERROR_SERVER, content = {@Content})
    })
    ResponseEntity<TaskGetDto> updateTask(@Parameter(name = PARAMETER_TASK_UPDATE) @RequestBody @Valid TaskPostDto dto, @Parameter(name = PARAMETER_ID) @PathVariable Long id, @AuthenticationPrincipal User loggedUser);

    @Operation(summary = SUMARY_DELETE, description = DESCRIPTION_DELETE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {@Content}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_TASK, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<Void> deleteTask(@Parameter(name = PARAMETER_ID) @PathVariable Long id, @AuthenticationPrincipal User loggedUser);
}
