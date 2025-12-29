package com.ch.shop.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.shop.dto.Provider;
import com.ch.shop.model.member.ProviderDAO;

@Service
public class ProviderServiceImpl implements ProviderService{
	
	@Autowired
	ProviderDAO providerDAO;
	
	@Override
	public List selectAll() {
		return providerDAO.selectAll();
	}

	@Override
	public Provider selectByName(String provider_name) {

		return providerDAO.selectByName(provider_name);
	}

}
