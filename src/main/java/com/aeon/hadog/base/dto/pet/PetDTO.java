package com.aeon.hadog.base.dto.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PetDTO {

    @NotBlank(message = "반려견 이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]+$", message = "반려견 이름에는 특수문자, 숫자를 포함할 수 없습니다.")
    private String name;
    private String breed;
    private String sex;
    private Boolean neuter;
    private String image;
    private Integer age;
    private String feature;

}
