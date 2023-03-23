package com.project.demo.documentation;

import com.project.demo.dto.*;
import com.project.demo.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static com.project.demo.util.Contants.UserApi.*;
import static com.project.demo.util.Contants.HttpCodes.*;

@Tag(name = TAG_NAME, description = TAG_DESCRIPTION)
public interface IAuthController {

    @Operation(summary = SUMARY_ADD, description = DESCRIPTION_ADD)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_CREATED, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content}),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = ERROR_SERVER, content = {@Content})
    })
    ResponseEntity<UserGetDto> registerUser(UserPostDto dto);

    @Operation(summary = SUMARY_LOGIN, description = DESCRIPTION_LOGIN)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_USER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<LoginResponseDto> login(LoginRequestDto dto);

    @Operation(summary = SUMARY_GET_ID, description = DESCRIPTION_GET_ID)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_USER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<UserGetDto> getUserById(Long id);

    @Operation(summary = SUMARY_GET_ALL, description = DESCRIPTION_GET_ALL)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<List<UserGetDto>> getAllUsers();

    @Operation(summary = SUMARY_UPDATE, description = DESCRIPTION_UPDATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_USER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content}),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = ERROR_SERVER, content = {@Content})
    })
    ResponseEntity<UserGetDto> updateUser(UserPostUpdateDto dto, Long id, User loggedUser);

    @Operation(summary = SUMARY_DELETE, description = DESCRIPTION_DELETE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {@Content}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_USER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<Void> deleteUser(Long id, User loggedUser);
}
