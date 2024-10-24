package com.springcore.web.main.service;

import java.util.List;
import java.util.Map;

import com.springcore.web.main.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
	
	@Autowired
	private MainMapper mainMapper;

	public List<Map<String, Object>> selectMainInfo() {
		return mainMapper.selectMainInfo();
	}
}
