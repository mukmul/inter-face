package mukmul.inter_face.controller;

import lombok.RequiredArgsConstructor;
import mukmul.inter_face.dto.request.UserUpdateRequestDto;
import mukmul.inter_face.dto.response.UserDeleteResponseDto;
import mukmul.inter_face.dto.request.UserRequestDto;
import mukmul.inter_face.dto.response.UserResponseDto;
import mukmul.inter_face.dto.response.UserUpdateResponseDto;
import mukmul.inter_face.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email) {
        UserResponseDto responseDto = userService.findByEmail(email);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> responseDtoList = userService.findAll();
        return ResponseEntity.ok(responseDtoList);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserDeleteResponseDto> deleteUser(@PathVariable Long id) {
        UserDeleteResponseDto responseDto = userService.delete(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDto requestDto) {
        UserUpdateResponseDto responseDto = userService.update(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
