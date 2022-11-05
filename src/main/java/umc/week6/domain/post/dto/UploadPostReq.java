package umc.week6.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UploadPostReq {

    @Schema(type = "String", example = "게시글 제목", description = "제목")
    @NotBlank
    private String title;

    @Schema(type = "String", example = "게시글 내용", description = "내용")
    @NotNull
    private String content;

    @Schema(type = "Boolean", example = "true", description = "익명 여부")
    @NotNull
    private Boolean anonymous;

    @Schema(type = "Array", example = "취미", description = "해쉬태그")
    private List<String> hashtags;

}
