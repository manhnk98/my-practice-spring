package com.nkm.mypracticespring.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseList<T> {

    private List<T> data;

    private int page;

    private int size;

    private int totalPage;

    private long totalElement;

}
