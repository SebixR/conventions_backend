package com.example.conventions_backend.dto;

import com.example.conventions_backend.entities.Tag;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class TagDto {
    Long id;
    String tag;

    public TagDto fromTag(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setTag(tag.getTag());
        return tagDto;
    }
}
