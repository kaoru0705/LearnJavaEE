package com.ch.galleryspring.model.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	PersonDAO personDAO;
	
	@Override
	public void regist(List nameList, List imgList) {
		// TODO Auto-generated method stub
		
	}

}
