package com.iyihua.ispiderman.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {

	private String title;
	private String content;
	private String subject;
	private String link;
	private Date date;
	private String images;
}
