package com.ch.tickethub.dto;

import lombok.Data;

@Data
public class Round {
	private int round_id;
	private String round_date;
	private String round_start_time;		// 00:00
	private boolean is_cancelled;
	
	private Work work;
	private Place place;
	
}
