package com.manta.Manta.community.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.manta.Manta.common.model.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    private Long cid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private Long bid;

    @NotBlank(message = "값을 입력해주세요.")
    @Size(min = 1, max = 100, message = "댓글은 최대 100자 입니다.")
    private String cm;

    private String userName;


}