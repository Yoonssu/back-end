package com.aeon.hadog.base.dto.shelter;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ListShelterPostDTO {
    private Long shelterPostId;
    private String title;
    private String thumbnail;

    private String sex;

    private String age;
}
