package com.manta.Manta.community.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardListDTO {
    private Long bid;
    private String title;
    private String userName;
    private long hit;
    private long comment;
    private String writeDt;
    @Builder.Default
    private String imagePath = "";

    @Builder
    public BoardListDTO(final long bid, String title, String userName, long hit, long comment, String writeDt, String imagePath){
        this.bid = bid;
        this.title = title;
        this.userName = userName;
        this.hit = hit;
        this.comment = comment;
        this.writeDt = writeDt;
        this.imagePath = imagePath;
    }

    public static BoardListDTO res(final long bid, String title, String userName, long hit, long comment, String writeDt, String imagePath) {
        return BoardListDTO.builder()
                .bid(bid)
                .title(title)
                .userName(userName)
                .hit(hit)
                .comment(comment)
                .writeDt(writeDt)
                .imagePath(imagePath)
                .build();
    }
}