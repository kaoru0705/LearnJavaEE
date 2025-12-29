package com.ch.shop.controller.shop;

import java.util.List;

import com.ch.shop.dto.Provider;

public interface ProviderService {
	public List selectAll();
	public Provider selectByName(String provider_name);
}
